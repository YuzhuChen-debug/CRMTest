package www.bjpowernode.crm.workbench.Services;

import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> getContactsList(String name);
}
