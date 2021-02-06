package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;


public interface ActivityDao {
    int save(Activity a );

    int getCount(Map<String, Object> map);

    List<Activity> getActivityList(Map<String, Object> map);

    int deleteActivityById(String[] ids);
}
