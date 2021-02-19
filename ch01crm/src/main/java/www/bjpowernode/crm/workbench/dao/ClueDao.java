package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Clue;

public interface ClueDao {


    int saveClue(Clue c);

    Clue getClue(String id);
}
