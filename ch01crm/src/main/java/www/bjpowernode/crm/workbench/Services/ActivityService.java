package www.bjpowernode.crm.workbench.Services;


import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;

import java.util.Map;

public interface ActivityService {

    boolean save(Map<String, Object> map) throws SaveActivityErrorException;
}
