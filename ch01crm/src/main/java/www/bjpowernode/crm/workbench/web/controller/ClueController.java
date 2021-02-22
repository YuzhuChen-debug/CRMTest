package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.UserListErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.ClueService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;
import www.bjpowernode.crm.workbench.Services.Imp.ClueServiceImpl;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.Clue;
import www.bjpowernode.crm.workbench.domain.Tran;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        }else if("/workbench/clue/getActivityListByClueId.do".equals(path)){
            getActivityListByClueId(request,response);
        }else if("/workbench/clue/unbund.do".equals(path)){
            unbund(request,response);
        }else if("/workbench/clue/showActivityListNotClueId.do".equals(path)){
            showActivityListNotClueId(request,response);
        }else if("/workbench/clue/bund.do".equals(path)){
            bund(request,response);
        }else if("/workbench/Clue/showClueList.do".equals(path)){
            showClueList(request,response);
        }else if("/workbench/Clue/showActivityList.do".equals(path)){
            showActivityList(request,response);
        }else if("/workbench/Clue/clueTran.do".equals(path)){
            convert(request,response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行转换操作");
        String flag = request.getParameter("flag");
        String clueId = request.getParameter("clueId");
        Tran t = null;
        if("0".equals(flag)){
            t=new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            String createBy = ((User)request.getSession().getAttribute("user")).getName();
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
            t.setId(id);
            t.setActivityId(activityId);
            t.setMoney(money);
            t.setStage(stage);
            t.setName(name);
            t.setExpectedDate(expectedDate);
        }
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success = cs.convert(clueId,t,createBy);
        if(success){
            response.sendRedirect(request.getContextPath()+"workbench/clue/index.jsp");
        }
    }

    private void showActivityList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行根据名称查询市场活动列表操作");
        String name = request.getParameter("name");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            List<Activity> aList = as.showActivityList(name);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("aList",aList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }
    }

    private void showClueList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行展示线索列表");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        try{
            List<Clue> cList = cs.showClueList();
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("cList",cList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到关联市场活动操作");
        String cid = request.getParameter("cid");
        String aids[] = request.getParameterValues("aid");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        try{
            boolean success = cs.bund(cid,aids);
            PrintJson.printJsonFlag(response,success);
        }catch (Exception e){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }
    }

    private void showActivityListNotClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询没有关联线索id的市场活动列表");
        String clueId = request.getParameter("clueId");
        String aname = request.getParameter("aname");
        Map<String,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("aname",aname);
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            List<Activity> aList = as.showActivityListNotClueId(map);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("aList",aList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }

    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行解除市场关联操作");
        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        try{
            boolean success = cs.unbund(id);
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

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("通过线索获取市场活动列表");
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            List<Activity> aList = as.getActivityListByClueId(id);
            Map<String,Object> map1 = new HashMap<>();
            map1.put("success",true);
            map1.put("aList",aList);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            String msg = e.getMessage();
            e.printStackTrace();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
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
