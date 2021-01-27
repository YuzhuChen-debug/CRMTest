package www.bjpowernode.crm.settings.dao;

import www.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

    public interface UserDao {
    User login(Map<String, Object> map);

        List<User> userList();
    }
