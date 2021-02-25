package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.UserListErrorException;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.Imp.TranServiceImpl;
import www.bjpowernode.crm.workbench.Services.TranService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TransactionController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到交易控制器");
        String path =request.getServletPath();
        if("/workbench/transaction/create.do".equals(path)){
            createPage(request,response);
        }else if("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
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
