package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsDao {

    int addContacts(Contacts contacts);

    List<Contacts> getContactsList(String name);
}
