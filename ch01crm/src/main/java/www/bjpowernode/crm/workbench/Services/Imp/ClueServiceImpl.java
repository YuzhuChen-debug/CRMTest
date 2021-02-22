package www.bjpowernode.crm.workbench.Services.Imp;


import www.bjpowernode.crm.Exceptions.ClueErrorException;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.workbench.Services.ClueService;
import www.bjpowernode.crm.workbench.dao.ClueDao;
import www.bjpowernode.crm.workbench.domain.Clue;
import www.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import www.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;


public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    @Override
    public boolean saveClue(Clue c) {
        boolean success = true;
        int count = clueDao.saveClue(c);
        if(count!=1){
            success =false;
        }
        return success;
    }

    @Override
    public Clue showDetail(String id) {
        Clue c = clueDao.getClue(id);
        return c;
    }

    @Override
    public boolean unbund(String id) throws ClueErrorException {
        boolean success = true;
        int count = clueDao.unbund(id);
        if(count!=1){
            throw new ClueErrorException("解除市场活动关联失败");
        }
        return success;
    }

    @Override
    public boolean bund(String cid, String[] aids) throws ClueErrorException {
        boolean success = true;
        ClueActivityRelation car = new ClueActivityRelation();
        for(String aid:aids){
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);
            int count = clueDao.bund(car);
            if(count!=1){
                throw new ClueErrorException("关联市场活动信息失败");
            }
        }
        return success;
    }

    @Override
    public List<Clue> showClueList() throws ClueErrorException {
        List<Clue> cList = clueDao.getClueList();
        if(cList==null){
            throw new ClueErrorException("查询线索列表错误");
        }
        return cList;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {

        return false;
    }
}
