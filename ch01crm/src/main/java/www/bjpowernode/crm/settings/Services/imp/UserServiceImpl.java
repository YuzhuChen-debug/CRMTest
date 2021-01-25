package www.bjpowernode.crm.settings.Services.imp;

import www.bjpowernode.crm.Exceptions.LoginErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.SqlSessionUtil;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.dao.UserDao;
import www.bjpowernode.crm.settings.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
     private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginErrorException {
        System.out.println("进入到用户登录服务层");
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);
        String nowTime = DateTimeUtil.getSysTime();
        if(user==null){
            throw new LoginErrorException("登录账号或者密码错误");
        }
        if(!user.getAllowIps().contains(ip)){
            throw new LoginErrorException("ip地址受限");
        }
        if("0".equals(user.getLockState())){
            throw new LoginErrorException("账号状态已锁定");
        }
        if(user.getExpireTime().compareTo(nowTime)<0){
            throw new LoginErrorException("登录时间受限");
        }
        return user;
    }
}
