package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    int addTran(Tran t);

    List<Tran> getPageList(int pageCount, int pageSize, Tran t);


    int getCount(int pageCount, int pageSize, Tran t);
}
