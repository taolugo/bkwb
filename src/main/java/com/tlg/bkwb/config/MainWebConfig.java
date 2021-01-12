package com.tlg.bkwb.config;

import com.tlg.bkwb.intercepter.LoginInterceptor;
import com.tlg.bkwb.intercepter.OnLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tlg
 * @create 2021-01-07 23:26
 */
@Configuration
public class MainWebConfig implements WebMvcConfigurer {

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/cart/**","/order/**","/user/**")
                .excludePathPatterns("/","/index.html","/index","/login.html");

        registry.addInterceptor(new OnLoginInterceptor())
                .addPathPatterns("/login.html","/login","/register.html","/register");

    }
}
