package com.meeting.common.config.filter;

import com.meeting.common.util.SpringContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: XSS攻击拦截器
 */
@Component
@WebFilter(urlPatterns = "/")
public class XssFilter implements Filter {

    public  static Logger logger = LogManager.getLogger(XssFilter.class);

    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 16:45
     * @description: XSS请求拦截
     * @param: [request, response, filterChain]
     * @return: void
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        //获取配置合法路径
        String path= SpringContextHolder.getApplicationContext().getEnvironment().getProperty("xss.permission.request");
        //获取当前请求路径
        String requestPath=httprequest.getRequestURI();
        logger.info("The requested url："+ requestPath);
        //把合法路径转换成数组
        List<String> pathList = Arrays.asList(path.split(","));
        //如果是配置路径不拦截
        if(pathList.contains(requestPath)) {
            filterChain.doFilter(request, response);
        } else {
            //不是配置路径拦截
            filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

}
