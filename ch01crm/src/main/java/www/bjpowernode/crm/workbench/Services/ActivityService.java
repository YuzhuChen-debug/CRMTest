package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.DeleteActivityAndActivityRemarkErrorException;
import www.bjpowernode.crm.Exceptions.pageListErrorException;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {

    boolean save(Activity a) ;

    CountAndActivityVO<Activity> getCountAndActivity(Map<String, Object> map) throws pageListErrorException;

    boolean delete(String[] ids) throws DeleteActivityAndActivityRemarkErrorException;

    Map<String, Object> getUserListAndActivity(String id);
}
