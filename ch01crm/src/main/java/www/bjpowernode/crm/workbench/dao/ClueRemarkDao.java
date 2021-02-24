package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getRemarkListByClueId(String clueId);

    int deleteClueRemarkByClueId(String clueId);
}
