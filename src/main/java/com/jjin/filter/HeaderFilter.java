package com.jjin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@WebFilter("/*")
public class HeaderFilter implements Filter {
    private final String CHECKING_HEADER_NAME = "test-token";
    ConcurrentMap<String, String> headers = new ConcurrentHashMap<>();;
    Cookie[] cookies = null;
    HttpSession session = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        synchronized (headers) {
            if (headers.isEmpty() && req.getHeader(CHECKING_HEADER_NAME) != null) {
                // update headers with authenticated token/cookie
                // TODO:
                headers.put(CHECKING_HEADER_NAME, req.getHeader(CHECKING_HEADER_NAME));
                HttpSession tempSession = req.getSession(true);
                tempSession.setAttribute("Mark", "inited in filter");
                cookies = req.getCookies();
                session = req.getSession();
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                HttpServletRequestWrapper requestWrapper = new HeaderFilterHttpServletRequestWrapper(req, headers, cookies, session);
                filterChain.doFilter(requestWrapper, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
