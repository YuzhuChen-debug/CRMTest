package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int selectActivityRemarkById(String[] ids);

    int deleteActivityRemarkById(String[] ids);

    List<ActivityRemark> getActivityRemarkDetailByAid(String aid);

    int removeRemarkById(String id);
}
