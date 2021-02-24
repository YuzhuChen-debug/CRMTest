package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Clue;
import www.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueDao {


    int saveClue(Clue c);

    Clue getClue(String id);

    int unbund(String id);

    int bund(ClueActivityRelation car);

    List<Clue> getClueList();

    Clue getClueListByClueId(String clueId);

    int delete(String clueId);
}
