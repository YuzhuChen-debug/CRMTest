package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Exceptions.ActivityDetialErrorException;
import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.Services.TranService;
import www.bjpowernode.crm.workbench.dao.CustomerDao;
import www.bjpowernode.crm.workbench.dao.TranDao;
import www.bjpowernode.crm.workbench.dao.TranHistoryDao;
import www.bjpowernode.crm.workbench.domain.Customer;
import www.bjpowernode.crm.workbench.domain.Tran;
import www.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public class TranServiceImpl implements TranService {
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> nameList = customerDao.getCustomerName(name);
        return nameList;
    }

    @Override
    public boolean save(Tran t, String customerName) throws SaveActivityErrorException {
        boolean success = true;
        //首先根据客户名称精准查询是否存在对应的客户
        Customer customer = customerDao.getCustomerByname(customerName);
        if(customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(t.getOwner());
            customer.setName(customerName);
            customer.setCreateBy(t.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(t.getContactSummary());
            customer.setDescription(t.getDescription());
            customer.setNextContactTime(t.getNextContactTime());
            int count = customerDao.addNewCustomer(customer);
            if(count!=1){
                throw new SaveActivityErrorException("添加客户信息失败");
            }
        }
        t.setCustomerId(customer.getId());
        int count2 = tranDao.addTran(t);
        if(count2!=1){
            throw new SaveActivityErrorException("添加交易信息失败");
        }
        //添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(t.getStage());
        tranHistory.setCreateBy(t.getCreateBy());
        tranHistory.setTranId(t.getId());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setExpectedDate(t.getExpectedDate());
        tranHistory.setMoney(t.getMoney());
        int count3= tranHistoryDao.addTranHistory(tranHistory);
        if(count3!=1){
            throw new SaveActivityErrorException("添加交易历史失败");
        }

        return success;
    }

    @Override
    public CountAndActivityVO<Tran> getPageList(Map<String, Object> map) throws ActivityDetialErrorException {
           CountAndActivityVO caav = new CountAndActivityVO();
           List<Tran> aList = tranDao.getPageList(map);
           if(aList==null){
               throw new ActivityDetialErrorException("查询交易列表失败");
           }
           int count = tranDao.getCount(map);
           if(count==-1){
               throw new ActivityDetialErrorException("查询总数据条数失败");
           }
           caav.setaList(aList);
           caav.setCount(count);
           return  caav;
    }

    @Override
    public Tran detail(String id) throws ActivityDetialErrorException {
        System.out.println("进入到查询业务层");
        Tran t = tranDao.getDetial(id);
        if(t==null){
            throw new ActivityDetialErrorException("查询详细信息错误");
        }
        return t;
    }

    @Override
    public List<TranHistory> showHistoryList(String id) throws ActivityDetialErrorException {
        List<TranHistory> thList = tranHistoryDao.getTranHistoryList(id);
        if(thList==null){
            throw new ActivityDetialErrorException("查询市场活动历史信息失败");
        }
        return thList;
    }

    @Override
    public boolean changeStage(Tran t) throws ActivityDetialErrorException {
        int count  = tranDao.changStage(t);
        if(count!=1){
            throw new ActivityDetialErrorException("更新阶段失败");
        }
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getEditBy());
        th.setTranId(t.getId());
        int count1 = tranHistoryDao.addTranHistory(th);
        if(count1!=1){
            throw new ActivityDetialErrorException("添加交易历史信息失败");
        }
        return true;
    }


}
