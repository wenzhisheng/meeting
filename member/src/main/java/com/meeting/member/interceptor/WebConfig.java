package com.meeting.member.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 访问配置
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig implements WebMvcConfigurer,ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    public WebConfig() {
        super();
    }

    @Bean
    LoginHandler localInterceptor() {
        return new LoginHandler();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        //swagger ui页面静态资源读取路径
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截规则
        registry.addInterceptor(localInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/ws/**",
                "/static/**",
                "/swagger-ui.html",
                "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/common/getVerifyCode",
                "/common/getSign",
                "/common/sendSms",
                "/common/sendMail",
                "/common/isIpWhite",
                "/common/cascadeList",
                "/user/authenticator",
                "/file/**",
                "/user/login",
                "/user/logout",
                "/member/insert",
                "/member/getMember",
                "/member/changePassword");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}