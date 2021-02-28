package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranService  {

    List<String> getCustomerName(String name);

    boolean save(Tran t, String customerName) throws SaveActivityErrorException;

    CountAndActivityVO<Tran> getPageList(int pageCount,int pageSize,Tran t);
}
