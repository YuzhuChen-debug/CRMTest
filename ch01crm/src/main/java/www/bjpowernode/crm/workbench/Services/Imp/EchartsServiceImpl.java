package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Exceptions.ActivityRemarkDetailErrorException;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.EchartsService;
import www.bjpowernode.crm.workbench.dao.TranDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EchartsServiceImpl implements EchartsService {
    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    @Override
    public Map<String, Object> getCountByStage() throws ActivityRemarkDetailErrorException {
        Map<String,Object> map = new HashMap<>();
        int count =tranDao.getCount1();
        if(count==-1){
            throw new ActivityRemarkDetailErrorException("查询总条数错误");
        }
        List<Map<String, Object>> dataList = tranDao.getcountAndStage();
        if(dataList==null){
            throw new ActivityRemarkDetailErrorException("查询分组条数错误");
        }
        map.put("total",count);
        map.put("dataList",dataList);
        return map;
    }
}
