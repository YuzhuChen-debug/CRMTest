package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;
import www.bjpowernode.crm.workbench.domain.Activity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");
        String path =request.getServletPath();
        if("/workbench/Activity/UserList.do".equals(path)){
            userList(request,response);
        }else if("/workbench/Activity/save.do".equals(path)){
            save(request,response);
        }



    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到保存活动信息控制器");
        //获取市场活动参数信息
        String id= UUIDUtil.getUUID();
        String owner= request.getParameter("owner");
        String name= request.getParameter("name");
        String startDate= request.getParameter("startDate");
        String endDate= request.getParameter("endDate");
        String cost= request.getParameter("cost");
        String description= request.getParameter("description");
        String createTime= DateTimeUtil.getSysTime();
        String createBy= ((User)request.getSession().getAttribute("user")).getName();
        //把信息放到一个map集合当中
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateBy(createBy);
        a.setCreateTime(createTime);
        ActivityService as = (ActivityService) ServiceFactory.getService( new ActivityServiceImp());
        boolean flag = as.save(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void userList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到用户列表查询控制器");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try{
            List<User> uList = us.userList();
            PrintJson.printJsonObj(response,uList);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();

        }
    }


}
