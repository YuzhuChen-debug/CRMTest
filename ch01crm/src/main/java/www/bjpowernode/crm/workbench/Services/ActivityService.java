package www.bjpowernode.crm.workbench.Services;


import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.workbench.domain.Activity;

public interface ActivityService {

    boolean save(Activity a) ;
}
