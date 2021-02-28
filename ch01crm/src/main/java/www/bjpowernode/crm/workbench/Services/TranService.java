package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.ActivityDetialErrorException;
import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.domain.Tran;
import www.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService  {

    List<String> getCustomerName(String name);

    boolean save(Tran t, String customerName) throws SaveActivityErrorException;

    CountAndActivityVO<Tran> getPageList(Map<String,Object> map) throws ActivityDetialErrorException;

    Tran detail(String id) throws ActivityDetialErrorException;

    List<TranHistory> showHistoryList(String id) throws ActivityDetialErrorException;
}
