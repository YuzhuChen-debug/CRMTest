package www.bjpowernode.crm.settings.webapp.Controller;

import www.bjpowernode.crm.Utils.MD5Util;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户登录控制器");
        String path =request.getServletPath();
        if("/settings/user/login".equals(path)){
            login(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        String loginAct = request.getParameter("loginAct");
        String loginPwdStr = request.getParameter("loginPwd");
        String loginPwd = MD5Util.getMD5(loginPwdStr);
        String ip = request.getRemoteAddr();
        HttpSession session = request.getSession();
        try{
            User user = us.login(loginAct,loginPwd,ip);
            session.setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);
        }catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("flag",false);
            map.put("message",message);
            PrintJson.printJsonObj(response,map);
        }

    }


}
