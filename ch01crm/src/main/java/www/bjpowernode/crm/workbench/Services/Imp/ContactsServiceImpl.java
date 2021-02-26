package www.bjpowernode.crm.workbench.Services.Imp;

import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.workbench.Services.ContactsService;
import www.bjpowernode.crm.workbench.dao.ContactsDao;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.Contacts;

import java.util.List;

public class ContactsServiceImpl implements ContactsService {
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    @Override
    public List<Contacts> getContactsList(String name) {
        List<Contacts> coList = contactsDao.getContactsList(name);
        return coList;
    }
}
