package www.bjpowernode.crm.workbench.web.controller;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户登录控制器");
        String path =request.getServletPath();
        if("/workbench/Activity/xxx.do".equals(path)){
            //XXX(request,response);
        }



    }


}
