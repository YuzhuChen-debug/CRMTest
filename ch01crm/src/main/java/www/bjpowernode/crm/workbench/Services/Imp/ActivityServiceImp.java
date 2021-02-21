package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Exceptions.*;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.settings.dao.UserDao;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.dao.ActivityDao;
import www.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImp implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
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
    public CountAndActivityVO<Activity> getCountAndActivity(Map<String, Object> map) throws pageListErrorException {
        System.out.println("进入到列表查询业务层");
        CountAndActivityVO<Activity> caav = new CountAndActivityVO<>();
        int count = activityDao.getCount(map);
        if(count==-1){
            throw new pageListErrorException("查询总条数错误");
        }
        List<Activity> aList = activityDao.getActivityList(map);
        if(aList==null){
            throw new pageListErrorException("查询市场活动列表错误");
        }
        caav.setCount(count);
        caav.setaList(aList);
        return caav;
    }

    @Override
    public boolean delete(String[] ids) throws DeleteActivityAndActivityRemarkErrorException {
        System.out.println("进入到删除市场活动列表业务层");
        boolean success = true;
        //先查询需要删除的市场活动列表备注的总条数
        int count1 = activityRemarkDao.selectActivityRemarkById(ids);
        if(count1 ==-1){
            throw new DeleteActivityAndActivityRemarkErrorException("查询要删除的市场备注错误");
        }
        //删除市场活动列表
        int count2 = activityRemarkDao.deleteActivityRemarkById(ids);
        if(count2==-1){
            throw new DeleteActivityAndActivityRemarkErrorException("删除市场备注列表错误");
        }
        if(count1!=count2){
            throw new DeleteActivityAndActivityRemarkErrorException("删除的市场活动备注条数和需要删除的条数不符");
        }
        //删除市场活动列表
        int count3 = activityDao.deleteActivityById(ids);
        if(count3==-1){
            throw new DeleteActivityAndActivityRemarkErrorException("删除市场活动条数错误");
        }
        if(count3!=ids.length){
            throw new DeleteActivityAndActivityRemarkErrorException("删除市场活动条数和需要删除的条数不符");
        }
        return success;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<>();
        List<User> userList = userDao.userList();
        Activity activity = activityDao.getActivity(id);
        map.put("uList",userList);
        map.put("activity",activity);
        return map;
    }

    @Override
    public boolean update(Activity a) throws UpdateActivityErrorException {
        boolean success = true;
        int count = activityDao.update(a);
        if(count==-1){
            throw new UpdateActivityErrorException("修改市场活动列表错误");
        }
        return success;
    }

    @Override
    public Activity getActivityById(String id) throws ActivityDetialErrorException {
        Activity a = activityDao.getActivityById(id);
        if(a==null){
            throw new ActivityDetialErrorException("查询市场活动详细信息失败");
        }
        return a;
    }

    @Override
    public List<ActivityRemark> getActivityRemarkDetailByAid(String aid) throws ActivityRemarkDetailErrorException {
        List<ActivityRemark> arList = activityRemarkDao.getActivityRemarkDetailByAid(aid);
        if(arList==null){
            throw new ActivityRemarkDetailErrorException("获取备注信息错误");
        }
        return arList;
    }

    @Override
    public boolean removeRemarkById(String id) throws RemoveRemarkErrorException {
        boolean success =true;
        int count = activityRemarkDao.removeRemarkById(id);
        if(count==-1){
            throw new RemoveRemarkErrorException("删除市场活动备注失败");
        }
        return success;
    }

    @Override
    public List<Activity> getActivityListByClueId(String id) throws pageListErrorException {
        System.out.println("执行通过线索id查询市场活动信息");
        List<Activity> aList = activityDao.getActivityListByClueId(id);
        if(aList==null){
            throw new pageListErrorException("展示市场活动列表错误");
        }
        return aList;
    }

    @Override
    public List<Activity> showActivityListNotClueId(Map<String, Object> map) throws ActivityDetialErrorException {
        List<Activity> aList = activityDao.showActivityListNotClueId(map);
        if(aList==null){
            throw new ActivityDetialErrorException("查询市场活动列表失败");
        }
        return aList;
    }

    @Override
    public boolean saveActivityRemark(ActivityRemark ar) throws ActivityRemarkDetailErrorException {
        boolean success = true;
        int count = activityRemarkDao.saveActivityRemark(ar);
        if(count!=1){
            throw new ActivityRemarkDetailErrorException("添加市场活动备注失败");
        }
        return success;
    }

    @Override
    public boolean updateNodeContent(ActivityRemark ar) throws ActivityRemarkDetailErrorException {
        int count = activityRemarkDao.updateNodeContent(ar);
        if(count!=1){
            throw new ActivityRemarkDetailErrorException("修改市场活动备注错误");
        }
        return true;
    }


}
