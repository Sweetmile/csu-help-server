package csu.coderwu.csuhelp.wx.mini.config;

import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.db.service.StudentService;
import csu.coderwu.csuhelp.wx.mini.annotation.support.LoginStudentHandlerResolver;
import csu.coderwu.csuhelp.wx.mini.annotation.support.OpenIdHandlerResolver;
import csu.coderwu.csuhelp.wx.mini.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author : coderWu
 * @date : Created on 17:20 2018/5/27
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private RedisTokenManager redisTokenManager;
    @Autowired
    private StudentService studentService;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new OpenIdHandlerResolver());
        argumentResolvers.add(new LoginStudentHandlerResolver(studentService));
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(redisTokenManager));
    }

    @Bean
    public RedisTokenManager redisTokenManager() {
        return new RedisTokenManager();
    }
}
