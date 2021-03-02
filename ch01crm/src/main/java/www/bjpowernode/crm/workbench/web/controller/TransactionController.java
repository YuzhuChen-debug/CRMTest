package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.ActivityDetialErrorException;
import www.bjpowernode.crm.Exceptions.SaveActivityErrorException;
import www.bjpowernode.crm.Exceptions.UserListErrorException;
import www.bjpowernode.crm.Utils.DateTimeUtil;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.Utils.UUIDUtil;
import www.bjpowernode.crm.VO.CountAndActivityVO;
import www.bjpowernode.crm.settings.Services.UserService;
import www.bjpowernode.crm.settings.Services.imp.UserServiceImpl;
import www.bjpowernode.crm.settings.domain.User;
import www.bjpowernode.crm.workbench.Services.ActivityService;
import www.bjpowernode.crm.workbench.Services.ContactsService;
import www.bjpowernode.crm.workbench.Services.Imp.ActivityServiceImp;
import www.bjpowernode.crm.workbench.Services.Imp.ContactsServiceImpl;
import www.bjpowernode.crm.workbench.Services.Imp.TranServiceImpl;
import www.bjpowernode.crm.workbench.Services.TranService;
import www.bjpowernode.crm.workbench.domain.Activity;
import www.bjpowernode.crm.workbench.domain.Contacts;
import www.bjpowernode.crm.workbench.domain.Tran;
import www.bjpowernode.crm.workbench.domain.TranHistory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
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
        }else if("/workbench/transaction/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/transaction/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/transaction/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/transaction/showHistoryList.do".equals(path)){
            showHistoryList(request,response);
        }else if("/workbench/transaction/changeStage.do".equals(path)){
            changeStage(request,response);
        }else if("/workbench/transaction/xxx.do".equals(path)){
            //xxx(request,response);
        }
    }



    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行转换阶段操作");
        String stage  = request.getParameter("stage");
        String id = request.getParameter("id");
        String  money = request.getParameter("money");
        String  expectedDate = request.getParameter("expectedDate");
        Map<String,String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        String properity = pmap.get(stage);
        Tran t = new Tran();
        t.setProperity(properity);
        t.setEditTime(DateTimeUtil.getSysTime());
        t.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        t.setStage(stage);
        t.setId(id);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        try {
            boolean success = ts.changeStage(t);
            Map<String,Object> map1 = new HashMap<>();
            //System.out.println(coList);
            map1.put("success",true);
            map1.put("t",t);
            PrintJson.printJsonObj(response,map1);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }
    }

    private void showHistoryList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行展示历史列表操作");
        String id = request.getParameter("id");
        Map<String,String> pmap = (Map<String, String>) this.getServletContext().getAttribute("pmap");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        try {
            List<TranHistory> thList = ts.showHistoryList(id);
            for(TranHistory tranHistory:thList){
                String stage = tranHistory.getStage();
                String properity = pmap.get(stage);
                tranHistory.setProperity(properity);
            }
            Map<String,Object> map1 = new HashMap<>();
            //System.out.println(coList);
            map1.put("success",true);
            map1.put("thList",thList);
            PrintJson.printJsonObj(response,map1);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            Map<String,Object> map2 = new HashMap<>();
            //System.out.println(coList);
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("跳转到详细信息页");
        String id = request.getParameter("id");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        try {
            Tran t = ts.detail(id);
            String stage = t.getStage();
            Map<String,String> pmap = (Map<String, String>) this.getServletContext().getAttribute("pmap");
            String properity = pmap.get(stage);
            t.setProperity(properity);
            request.setAttribute("t",t);
            request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityDetialErrorException e) {
            e.printStackTrace();
        }
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行查询交易列表操作");
        String pageNoStr= request.getParameter("pageNo");
        int pageNo = Integer.parseInt(pageNoStr);
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        int pageCount = (pageNo-1)*pageSize;
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String customerId = request.getParameter("customerName");
        String contactsId = request.getParameter("contactsName");
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Map<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("customerId",customerId);
        map.put("contactsId",contactsId);
        map.put("pageCount",pageCount);
        map.put("pageSize",pageSize);
        try{
            CountAndActivityVO<Tran> caav = ts.getPageList(map);
            Map<String,Object> map1 = new HashMap<>();
            //System.out.println(coList);
            map1.put("success",true);
            map1.put("caav",caav);
            PrintJson.printJsonObj(response,map1);
        }catch(Exception e){
            e.printStackTrace();
            String msg = e.getMessage();
            System.out.println(msg);
            Map<String,Object> map2 = new HashMap<>();
            map2.put("success",false);
            map2.put("msg",msg);
            PrintJson.printJsonObj(response,map2);
        }

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行添加交易操作");
        String id= UUIDUtil.getUUID();
        String owner=request.getParameter("save-owner");
        String money=request.getParameter("save-money");
        String name=request.getParameter("save-name");
        String expectedDate=request.getParameter("save-expectedDate");
        String customerName=request.getParameter("save-customerName");
        String stage=request.getParameter("save-stage");
        String type=request.getParameter("save-type");
        String source=request.getParameter("save-source");
        String activityId=request.getParameter("save-activityId");
        String contactsId=request.getParameter("save-contactsId");
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String createTime= DateTimeUtil.getSysTime();
        String description=request.getParameter("save-description");
        String contactSummary=request.getParameter("save-contactSummary");
        String nextContactTime=request.getParameter("save-nextContactTime");
        Tran t = new Tran();
        t.setId(id);
        t.setOwner(owner);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setStage(stage);
        t.setType(type);
        t.setSource(source);
        t.setName(name);
        t.setActivityId(activityId);
        t.setContactsId(contactsId);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setDescription(description);
        t.setContactSummary(contactSummary);
        t.setNextContactTime(nextContactTime);
        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());
        try {
            boolean success = ts.save(t,customerName);
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SaveActivityErrorException e) {
            e.printStackTrace();

            //这里怎么把一场信息给save.jsp呢,并且在save.jsp当中alert()出来;
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
