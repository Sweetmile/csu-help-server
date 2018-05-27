package csu.coderwu.csuhelp.wx.mini.annotation.support;

import csu.coderwu.csuhelp.cache.bean.TokenModel;
import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.wx.mini.annotation.LoginId;
import csu.coderwu.csuhelp.wx.mini.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : coderWu
 * @date : Created on 17:40 2018/5/26
 */
@Component
public class LoginIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RedisTokenManager redisTokenManager;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class) &&
                methodParameter.hasParameterAnnotation(LoginId.class);
    }

    @Override
    public String resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(Global.TOKEN_KEY);
        if (token.isEmpty()) {
            return null;
        }
        return redisTokenManager.getId(new TokenModel().setToken(token));
    }
}
