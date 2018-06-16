package com.jjin.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class HeaderFilterHttpServletRequestWrapper extends HttpServletRequestWrapper {
    ConcurrentMap<String, String> headers = null;
    Cookie[] cookies = null;
    HttpSession session = null;
    public HeaderFilterHttpServletRequestWrapper(HttpServletRequest request, ConcurrentMap<String, String> headers, Cookie[] cookies, HttpSession session) {
        super(request);
        this.headers = headers;
        this.cookies = cookies;
        this.session = session;
    }

    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest)super.getRequest();
    }

    public String getHeader(String name) {
        if(headers.containsKey(name)) {
            return headers.get(name);
        }
        else {
            return this._getHttpServletRequest().getHeader(name);
        }
    }

    public Enumeration<String> getHeaders(String name) {
        if(headers.containsKey(name)) {
            List<String> list = new ArrayList<>();
            list.add(headers.get(name));
            return Collections.enumeration(list);
        }
        else {
            return this._getHttpServletRequest().getHeaders(name);
        }
    }

    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>(headers.keySet());
        Enumeration<String> realNames = this._getHttpServletRequest().getHeaderNames();
        while(realNames.hasMoreElements()) {
            names.add(realNames.nextElement());
        }
        return Collections.enumeration(names);
    }

    @Override
    public Cookie[] getCookies() {
        return cookies;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return session;
    }

    @Override
    public HttpSession getSession() {
        return session;
    }
}
