package www.bjpowernode.crm.workbench.Services.Imp;



import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.dao.ActivityDao;
import www.bjpowernode.crm.workbench.domain.Activity;

public class ActivityServiceImp implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public boolean save(Activity a){
        System.out.println("进入到保存活动业务层");
        boolean flag = true;
        int count = activityDao.save(a);
        if(count!=1){
            flag = false;
        }
        return flag;
    }
}
