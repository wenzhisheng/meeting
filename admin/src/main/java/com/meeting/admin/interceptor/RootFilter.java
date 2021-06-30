package com.meeting.admin.interceptor;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: root过滤
 */
@Component
@WebFilter(urlPatterns = "/")
public class RootFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
//        System.out.println(".......init.......");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        System.out.println(".......doFilter.......");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
//        System.out.println(".......destroy.......");
    }
}
