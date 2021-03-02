package www.bjpowernode.crm.workbench.web.controller;

import www.bjpowernode.crm.Exceptions.ActivityRemarkDetailErrorException;
import www.bjpowernode.crm.Utils.PrintJson;
import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.workbench.Services.EchartsService;
import www.bjpowernode.crm.workbench.Services.Imp.EchartsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EchartsController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入图表控制器");
        String path = request.getServletPath();
        if("/workbench/echarts/getCountByStage.do".equals(path)){
            getCountByStage(request,response);
        }
    }

    private void getCountByStage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询总条数和根据stage分组条数");
        EchartsService es = (EchartsService) ServiceFactory.getService(new EchartsServiceImpl());
        Map<String,Object> map = null;
        try {
            map = es.getCountByStage();
            PrintJson.printJsonObj(response,map);
        } catch (ActivityRemarkDetailErrorException e) {
            e.printStackTrace();
        }


    }
}
