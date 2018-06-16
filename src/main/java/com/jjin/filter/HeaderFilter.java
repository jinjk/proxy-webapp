package com.jjin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@WebFilter("/*")
public class HeaderFilter implements Filter {
    ConcurrentMap<String, String> headers = null;
    Cookie[] cookies = null;
    HttpSession session = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        synchronized (headers) {
            if (headers.isEmpty()) {
                // update headers with authenticated token/cookie
                // TODO:
                headers = null;
                cookies = null;
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
