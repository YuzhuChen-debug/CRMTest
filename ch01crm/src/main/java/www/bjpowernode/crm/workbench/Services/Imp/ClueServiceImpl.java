package www.bjpowernode.crm.workbench.Services.Imp;


import www.bjpowernode.crm.Exceptions.ClueErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.workbench.Services.ClueService;
import www.bjpowernode.crm.workbench.dao.*;
import www.bjpowernode.crm.workbench.domain.*;

import java.util.List;


public class ClueServiceImpl implements ClueService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);


    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


    @Override
    public boolean saveClue(Clue c) {
        boolean success = true;
        int count = clueDao.saveClue(c);
        if(count!=1){
            success =false;
        }
        return success;
    }

    @Override
    public Clue showDetail(String id) {
        Clue c = clueDao.getClue(id);
        return c;
    }

    @Override
    public boolean unbund(String id) throws ClueErrorException {
        boolean success = true;
        int count = clueDao.unbund(id);
        if(count!=1){
            throw new ClueErrorException("解除市场活动关联失败");
        }
        return success;
    }

    @Override
    public boolean bund(String cid, String[] aids) throws ClueErrorException {
        boolean success = true;
        ClueActivityRelation car = new ClueActivityRelation();
        for(String aid:aids){
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);
            int count = clueDao.bund(car);
            if(count!=1){
                throw new ClueErrorException("关联市场活动信息失败");
            }
        }
        return success;
    }

    @Override
    public List<Clue> showClueList() throws ClueErrorException {
        List<Clue> cList = clueDao.getClueList();
        if(cList==null){
            throw new ClueErrorException("查询线索列表错误");
        }
        return cList;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {
        boolean flag = true;
        //  我们的目的是把线索转换成联系人和客户,线索的关系表和备注表转换成联系人和客户的关系和备注表
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
            Clue clue = clueDao.getClueListByClueId(clueId);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
            String company = clue.getCompany();
            Customer customer = customerDao.getCustomerByname(company);

            if(customer==null){
                customer = new Customer();
                customer.setId(UUIDUtil.getUUID());
                customer.setWebsite(clue.getWebsite());
                customer.setPhone(clue.getPhone());
                customer.setOwner(clue.getOwner());
                customer.setNextContactTime(clue.getNextContactTime());
                customer.setAddress(clue.getAddress());
                customer.setDescription(clue.getDescription());
                customer.setContactSummary(clue.getContactSummary());
                customer.setCreateTime(DateTimeUtil.getSysTime());
                customer.setCreateBy(createBy);
                customer.setName(company);
                int count1 = customerDao.addNewCustomer(customer);
                if(count1!=1){
                    boolean falg = false;
                }


            }
        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setSource(clue.getSource());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setBirth("");
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int count2 = contactsDao.addContacts(contacts);
        if(count2!=1){
            flag = false;
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        //通过线索id获取到备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkDao.getRemarkListByClueId(clueId);
            for(ClueRemark clueRemark:clueRemarkList){
                //提取到的备注信息
                String noteContent =  clueRemark.getNoteContent();
                //把提取到的信息放到客户备注domain中
                CustomerRemark customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtil.getUUID());
                customerRemark.setNoteContent(noteContent);
                customerRemark.setCreateBy(createBy);
                customerRemark.setCreateTime(DateTimeUtil.getSysTime());
                customerRemark.setEditFlag("0");
                customerRemark.setCustomerId(customer.getId());
                int count3 = customerRemarkDao.addCustomerRemark(customerRemark);
                if(count3!=1){
                    flag = false;
                }

                //把提取到的信息放到联系人备注domain中
                ContactsRemark contactsRemark = new ContactsRemark();
                contactsRemark.setId(UUIDUtil.getUUID());
                contactsRemark.setNoteContent(noteContent);
                contactsRemark.setEditFlag("0");
                contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
                contactsRemark.setCreateBy(createBy);
                contactsRemark.setContactsId(contacts.getId());
                int count4 = contactsRemarkDao.addContactsRemark(contactsRemark);
                if(count4 != 1){
                    flag = false;
                }
            }
        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        //通过clueId查询到相关联的市场活动
            List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getActivityIdByClueId(clueId);
            for(ClueActivityRelation clueActivityRelation:clueActivityRelationList){
                String activityId = clueActivityRelation.getActivityId();
                ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
                contactsActivityRelation.setActivityId(activityId);
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                contactsActivityRelation.setContactsId(contacts.getId());
                int count5 = contactsActivityRelationDao.addContactsActivityRelation(contactsActivityRelation);
                if(count5 != 1 ){
                    flag = false;
                }
            }
        //(6) 如果有创建交易需求，创建一条交易
        if(t!=null){
            t.setSource(clue.getSource());
            t.setOwner(clue.getOwner());
            t.setContactSummary(clue.getContactSummary());
            t.setContactsId(contacts.getId());
            t.setNextContactTime(clue.getNextContactTime());
            t.setCustomerId(customer.getId());
            t.setDescription(clue.getDescription());

            int count6 = tranDao.addTran(t);
            if(count6!=1){
                flag = false;
            }
            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.getExpectedDate();
            tranHistory.setTranId(t.getId());
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            int count7 = tranHistoryDao.addTranHistory(tranHistory);
            if(count7!=1){
                flag = false;
            }
        }

        // (8) 删除线索备注
        //查询线索备注条数,如果查询结果为-1 报错，如果查询条数和删除条数不一致，报错,
        int count8 = clueRemarkDao.deleteClueRemarkByClueId(clueId);
        //(9) 删除线索和市场活动的关系,同理，查询关系条数，为-1，报错，如果数量不一致，报错
        int count9 = clueActivityRelationDao.deleteByClueId(clueId);
        //(10) 删除线索
        int count10 = clueDao.delete(clueId);

        return flag;
    }
}
