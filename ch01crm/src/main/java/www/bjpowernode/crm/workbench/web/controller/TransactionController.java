package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.UserListErrorException;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.ContactsService;
import www.bjpowernode.crm.workbench.Services.CustomerService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;
import www.bjpowernode.crm.workbench.Services.Imp.ContactsServiceImpl;
import www.bjpowernode.crm.workbench.Services.Imp.CustomerServiceImp;
import www.bjpowernode.crm.workbench.Services.Imp.TranServiceImpl;
import www.bjpowernode.crm.workbench.Services.TranService;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.Contacts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易控制器");
        String path =request.getServletPath();
        if("/workbench/transaction/create.do".equals(path)){
            createPage(request,response);
        }else if("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if("/workbench/transaction/getActivityList.do".equals(path)){
            getActivityList(request,response);
        }else if("/workbench/transaction/getContactsList.do".equals(path)){
            getContactsList(request,response);
        }
    }

    private void getContactsList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询客户列表");
        String name = request.getParameter("name");
        ContactsService cos = (ContactsService) ServiceFactory.getService(new ContactsServiceImpl());
        try{
            List<Contacts> coList = cos.getContactsList(name);
            Map<String,Object> map1 = new HashMap<>();
            //System.out.println(coList);
            map1.put("success",true);
            map1.put("coList",coList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }

    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表");
        String name = request.getParameter("name");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            List<Activity> aList = as.getActivityList(name);
            Map<String,Object> map1 = new HashMap<>();
            System.out.println(aList);
            map1.put("success",true);
            map1.put("aList",aList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行查询用户名称操作");
        String name = request.getParameter("name");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        List<String> nameList =  ts.getCustomerName(name);
        PrintJson.printJsonObj(response,nameList);
    }

    private void createPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到创建控制器");
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            List<User> uList = us.userList();
            request.setAttribute("uList",uList);
            request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
        } catch (UserListErrorException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
