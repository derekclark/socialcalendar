package uk.co.socialcalendar.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.info("in security filter");

        HttpServletResponse res = (HttpServletResponse) response;
        if (redirectRequestToLogin(request)){
            LOG.info("in redirect");
            res.sendRedirect("/login");
        } else{
            LOG.info("in chain.doFilter");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    public boolean redirectRequestToLogin(ServletRequest request){
        HttpServletRequest req = (HttpServletRequest) request;
        String userId = (String) req.getSession().getAttribute("USER_ID");
        LOG.info("userId="+userId);
        String uri = req.getRequestURI();
        LOG.info("uri = " + uri);

        HttpSession session = req.getSession();
        LOG.info("session="+session.toString());

        return (userId == null
                && !(uri.equals("/login")) && !(uri.contains("resource")) && !(uri.equals("/facebook")));
    }
}
