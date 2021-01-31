package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Exceptions.pageListErrorException;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.dao.ActivityDao;
import www.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

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

    @Override
    public CountAndActivityVO<List<Activity>> getCountAndActivity(Map<String, Object> map) throws pageListErrorException {
        CountAndActivityVO<List<Activity>> caav = new CountAndActivityVO<>();
        int count = activityDao.getCount(map);
        if(count!=1){
            throw new pageListErrorException("查询总条数错误");
        }
        List<Activity> aList = activityDao.getActivityList(map);
        if(aList==null){
            throw new pageListErrorException("查询市场活动列表错误");
        }
        caav.setCount(count);
        caav.setT(aList);
        return caav;
    }


}
