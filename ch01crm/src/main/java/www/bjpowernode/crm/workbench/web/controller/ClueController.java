package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.UserListErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ClueService;
import www.bjpowernode.crm.workbench.Services.Imp.ClueServiceImpl;
import www.bjpowernode.crm.workbench.domain.Clue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path =request.getServletPath();
        if("/workbench/Clue/UserList.do".equals(path)){
            UserList(request,response);
        }else if("/workbench/Clue/saveClue.do".equals(path)){
            saveClue(request,response);
        }else if("/workbench/clue/detail.do".equals(path)){
            showCluedetail(request,response);
        }
    }

    private void showCluedetail(HttpServletRequest request, HttpServletResponse response)  {
        System.out.println("展示线索详细信息");
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        try {
            Clue c = cs.showDetail(id);
            request.getSession().setAttribute("c",c);
            request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveClue(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行保存线索操作");
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        Clue c = new Clue();
        c.setAddress(address);
        c.setAppellation(appellation);
        c.setCompany(company);
        c.setWebsite(website);
        c.setState(state);
        c.setSource(source);
        c.setPhone(phone);
        c.setOwner(owner);
        c.setNextContactTime(nextContactTime);
        c.setMphone(mphone);
        c.setJob(job);
        c.setId(id);
        c.setFullname(fullname);
        c.setEmail(email);
        c.setDescription(description);
        c.setCreateTime(createTime);
        c.setCreateBy(createBy);
        c.setContactSummary(contactSummary);
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success = cs.saveClue(c);
        PrintJson.printJsonFlag(response,success);

    }

    private void UserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行获取用户列表操作");
        UserService us = (UserService) ServiceFactory.getService( new UserServiceImpl());
        try {
            List<User> userList = us.userList();
            PrintJson.printJsonObj(response,userList);
        } catch (UserListErrorException e) {
            e.printStackTrace();
        }

    }
}
