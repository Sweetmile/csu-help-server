package csu.coderwu.csuhelp.wx.mini.interceptor;

import csu.coderwu.csuhelp.cache.bean.TokenModel;
import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.db.service.StudentService;
import csu.coderwu.csuhelp.wx.mini.annotation.Authorization;
import csu.coderwu.csuhelp.wx.mini.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author : coderWu
 * @date : Created on 19:40 2018/5/27
 */
public class EsInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTokenManager redisTokenManager;
    @Autowired
    private StudentService studentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod ();
        //带有Authorization注解拦截
        if (method.getAnnotation (Authorization.class) != null) {
            String token = request.getHeader(Constants.TOKEN_KEY);
            if (token.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            String openid = redisTokenManager.getId(new TokenModel().setToken(token));
            Student student = studentService.getStudent(openid);
            if (student == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            request.setAttribute(Constants.INNER_OPENID, student);
        }
        return true;
    }

}
