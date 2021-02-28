package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int addTranHistory(TranHistory tranHistory);

    List<TranHistory> getTranHistoryList(String id);
}
