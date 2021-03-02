package www.bjpowernode.crm.Filters;

import www.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = request.getServletPath();
        if("/login.jsp".equals(path)||"/settings/user/login.do".equals(path)){
            filterChain.doFilter(request,response);
        }else{
            HttpSession session = request.getSession();
            User user =(User)session.getAttribute("user");
            if(user!=null){
                filterChain.doFilter(request,response);
            }else{
                response.sendRedirect(request.getContextPath()+"/index.html");
            }
        }
    }
}
