package www.bjpowernode.crm.workbench.Services;


import www.bjpowernode.crm.workbench.domain.Clue;

public interface ClueService {

    boolean saveClue(Clue c);


    Clue showDetail(String id);
}
