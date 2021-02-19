package www.bjpowernode.crm.workbench.Services.Imp;


import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.ClueService;
import www.bjpowernode.crm.workbench.dao.ClueDao;
import www.bjpowernode.crm.workbench.domain.Clue;


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
}
