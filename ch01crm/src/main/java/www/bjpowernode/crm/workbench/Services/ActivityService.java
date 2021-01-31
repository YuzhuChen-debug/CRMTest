package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.pageListErrorException;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean save(Activity a) ;


    CountAndActivityVO<List<Activity>> getCountAndActivity(Map<String, Object> map) throws pageListErrorException;
}
