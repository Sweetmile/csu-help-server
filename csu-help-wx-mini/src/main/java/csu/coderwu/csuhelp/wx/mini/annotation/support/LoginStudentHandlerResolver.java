package csu.coderwu.csuhelp.wx.mini.annotation.support;

import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.wx.mini.annotation.LoginStudent;
import csu.coderwu.csuhelp.wx.mini.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : coderWu
 * @date : Created on 20:00 2018/5/27
 */
public class LoginStudentHandlerResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class) &&
                methodParameter.hasParameterAnnotation(LoginStudent.class);
    }

    @Override
    public Student resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return (Student) nativeWebRequest.getAttribute(Constants.INNER_OPENID, RequestAttributes.SCOPE_REQUEST);
    }
}

