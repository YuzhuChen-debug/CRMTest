package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int addTran(Tran t);

    List<Tran> getPageList(Map<String,Object> map);


    int getCount(Map<String,Object> map);

    Tran getDetial(String id);

    int changStage(Tran t);

    int getCount1();

    List<Map<String, Object>> getcountAndStage();
}
