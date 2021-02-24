package www.bjpowernode.crm.workbench.dao;


import www.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    List<ClueActivityRelation> getActivityIdByClueId(String clueId);

    int deleteByClueId(String clueId);
}
