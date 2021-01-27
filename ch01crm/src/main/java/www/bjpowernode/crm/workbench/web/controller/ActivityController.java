package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;

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
        }else if("/workbench/Activity/save".equals(path)){
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
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("cost",cost);
        map.put("description",description);
        map.put("createTime",createTime);
        map.put("createBy",createBy);
        System.out.println(111111);
        ActivityService as = (ActivityService) ServiceFactory.getService( new ActivityServiceImp());
        try{
             boolean flag = as.save(map);
            PrintJson.printJsonFlag(response,flag);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map1 = new HashMap<>();
            map1.put("flag",false);
            map1.put("msg",msg);
            PrintJson.printJsonObj(response,map1);
        }
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
