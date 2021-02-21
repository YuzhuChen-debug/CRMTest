package www.bjpowernode.crm.workbench.Services;


import www.bjpowernode.crm.Exceptions.ClueErrorException;
import www.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueService {

    boolean saveClue(Clue c);


    Clue showDetail(String id);

    boolean unbund(String id) throws ClueErrorException;

    boolean bund(String cid, String[] aid) throws ClueErrorException;

    List<Clue> showClueList() throws ClueErrorException;
}
