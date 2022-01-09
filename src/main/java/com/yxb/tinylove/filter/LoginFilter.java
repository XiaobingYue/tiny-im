package com.yxb.tinylove.filter;

import com.yxb.tinylove.common.bean.Session;
import com.yxb.tinylove.common.util.SessionUtil;
import com.yxb.tinylove.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yxb
 * @since 2022.1.7
 */

@Slf4j
@WebFilter(filterName = "loginFilter", urlPatterns = "/")
public class LoginFilter implements Filter {

    private static final String PUBLIC = "/public";
    private static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (!uri.contains(PUBLIC)) {
            String authorization = req.getHeader(HEADER_AUTHORIZATION);
            String token = SessionUtil.getToken(authorization);
            if (StringUtils.isEmpty(token)) {
                throw new ServiceException("票据信息空");
            }
            Session session = SessionUtil.getSession(token);
            request.setAttribute(CURRENT_LOGIN_USER, session);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
