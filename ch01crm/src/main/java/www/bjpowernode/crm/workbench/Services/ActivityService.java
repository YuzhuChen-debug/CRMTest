package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.*;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean save(Activity a) ;

    CountAndActivityVO<Activity> getCountAndActivity(Map<String, Object> map) throws pageListErrorException;

    boolean delete(String[] ids) throws DeleteActivityAndActivityRemarkErrorException;

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a) throws UpdateActivityErrorException;

    Activity getActivityById(String id) throws ActivityDetialErrorException;

    List<ActivityRemark> getActivityRemarkDetailByAid(String aid) throws ActivityRemarkDetailErrorException;

    boolean removeRemarkById(String id) throws RemoveRemarkErrorException;

    List<Activity> getActivityListByClueId(String id) throws pageListErrorException;
}
