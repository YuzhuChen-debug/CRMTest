package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.ActivityRemark;

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
        }else if("/workbench/Activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/Activity/delete.do".equals(path)){
            delete(request,response);
        }else if("/workbench/Activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if("/workbench/Activity/update.do".equals(path)){
            update(request,response);
        }else if("/workbench/Activity/detail.do".equals(path)){
            showDetail(request,response);
        }else if("/workbench/Activity/showActivityRemark.do".equals(path)){
            showRemarkDetail(request,response);
        }else if("/workbench/Activity/removeRemark.do".equals(path)){
            removeRemark(request,response);
        }



    }

    private void removeRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到移除市场备注控制器");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            boolean success = as.removeRemarkById(id);
            PrintJson.printJsonFlag(response,success);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }

    private void showRemarkDetail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入显示市场活动备注信息控制器");
        String aid = request.getParameter("aid");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            List<ActivityRemark> arList = as.getActivityRemarkDetailByAid(aid);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("arList",arList);
            PrintJson.printJsonObj(response,map1);
        }catch (Exception e ){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到显示市场活动详细信息控制器");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            Activity a = as.getActivityById(id);
            request.getSession().setAttribute("a",a);
            request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到修改活动信息控制器");
        //获取市场活动参数信息
        String id= request.getParameter("id");
        String owner= request.getParameter("owner");
        String name= request.getParameter("name");
        String startDate= request.getParameter("startDate");
        String endDate= request.getParameter("endDate");
        String cost= request.getParameter("cost");
        String description= request.getParameter("description");
        String editTime= DateTimeUtil.getSysTime();
        String editBy= ((User)request.getSession().getAttribute("user")).getName();
        //把信息放到一个map集合当中
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateBy(editBy);
        a.setCreateTime(editTime);
        ActivityService as = (ActivityService) ServiceFactory.getService( new ActivityServiceImp());
        try{
            boolean flag = as.update(a);
            PrintJson.printJsonFlag(response,flag);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
        }

    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println("进入到用户列表查询和市场活动列表查询控制器");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
            Map<String,Object> map  = as.getUserListAndActivity(id);
            PrintJson.printJsonObj(response,map);

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

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到删除市场列表控制器");
        String ids[] = request.getParameterValues("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            boolean success = as.delete(ids);
            PrintJson.printJsonFlag(response,success);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
        }
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到列表查询控制器");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize =Integer.parseInt(pageSizeStr);
        String pageNumberStr = request.getParameter("pageNumber");
        int pageNumber = Integer.parseInt(pageNumberStr);
        int skipCount = pageSize*(pageNumber-1);
        System.out.println(pageSize);
        System.out.println(pageNumber);
        Map<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ActivityService as = (ActivityService) ServiceFactory.getService( new ActivityServiceImp());
        //第一个需要查询出aList,  第二个需要查询出总条数count(*)
        try{
            //返回一个VO类,VO类只能由后端向前端传递数据
            CountAndActivityVO<Activity> caav = as.getCountAndActivity(map);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("caav",caav);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
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


}
