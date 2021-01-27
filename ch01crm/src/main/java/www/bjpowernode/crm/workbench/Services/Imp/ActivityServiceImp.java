package www.bjpowernode.crm.workbench.Services.Imp;



import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.dao.ActivityDao;

import java.util.Map;

public class ActivityServiceImp implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public boolean save(Map<String,Object> map) throws SaveActivityErrorException {
        System.out.println("进入到保存活动业务层");
        int count = activityDao.save(map);
        if(count==0){
            throw new SaveActivityErrorException("添加市场活动信息异常");
        }
        return true;
    }
}
