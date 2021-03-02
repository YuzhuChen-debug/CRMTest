package www.bjpowernode.crm.Listeners;

import www.bjpowernode.crm.Utils.ServiceFactory;
import www.bjpowernode.crm.settings.Services.DictionaryService;
import www.bjpowernode.crm.settings.Services.imp.DictionaryServiceImp;
import www.bjpowernode.crm.settings.domain.DictionaryValue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitLinster implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("看一下是否启动全局作用域");
        //监听器是监听全局对象的生成
        //首先获取全局对象
        ServletContext application= event.getServletContext();
        DictionaryService ds = (DictionaryService) ServiceFactory.getService(new DictionaryServiceImp());
        //数据字典应该怎么获取
        /*
        *       根据code获取value值
        *       List<DictionaryValue> dvList = dictionaryValue.getValue();
        *       map.put("code1",dvList1);
        *       map.put("code2",dvList2);
        *       map.put("code3",dvList3);
        *       map.put("code4",dvList4);
        *       Map<String,List<DictionaryValue>> map  会得到这样一个map
        * */
        //获取数据字典
        Map<String, List<DictionaryValue>> map =ds.getDictionaryValueByCode();
        Set<String> set = map.keySet();
        for(String key:set){
            //把数据字典放到全局对象当中
            application.setAttribute(key,map.get(key));
        }
        //--------------------------------------------------------
        //获取可能性和交易阶段关系表
        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");
        //取出迭代器
        Map<String,String> pmap = new HashMap<>();
        Enumeration<String> it = rb.getKeys();
        while(it.hasMoreElements()){
            //取出每一个stage和properity 放到
            String key = it.nextElement();
            String value =rb.getString(key);
            pmap.put(key,value);
        }
        application.setAttribute("pmap",pmap);
    }
}
