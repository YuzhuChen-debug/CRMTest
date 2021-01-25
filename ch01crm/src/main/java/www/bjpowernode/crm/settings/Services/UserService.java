package www.bjpowernode.crm.settings.Services;

import www.bjpowernode.crm.Exceptions.LoginErrorException;
import www.bjpowernode.crm.settings.domain.User;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginErrorException;
}
