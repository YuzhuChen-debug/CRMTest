package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.TranService;
import www.bjpowernode.crm.workbench.dao.CustomerDao;

import java.util.List;

public class TranServiceImpl implements TranService {
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {
        List<String> nameList = customerDao.getCustomerName(name);
        return nameList;
    }
}
