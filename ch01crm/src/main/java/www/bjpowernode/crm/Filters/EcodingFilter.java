package www.bjpowernode.crm.Filters;

import javax.servlet.*;
import java.io.IOException;

public class EcodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入过滤字符编码的过滤器");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        filterChain.doFilter(req,resp);
    }
}
