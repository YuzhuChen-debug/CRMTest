package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.Exceptions.ActivityRemarkDetailErrorException;

import java.util.Map;

public interface EchartsService {
    Map<String, Object> getCountByStage() throws ActivityRemarkDetailErrorException;
}
