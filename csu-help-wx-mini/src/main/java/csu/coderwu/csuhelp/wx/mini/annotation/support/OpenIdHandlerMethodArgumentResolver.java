package csu.coderwu.csuhelp.wx.mini.annotation.support;

import csu.coderwu.csuhelp.cache.bean.TokenModel;
import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.wx.mini.annotation.OpenId;
import csu.coderwu.csuhelp.wx.mini.config.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : coderWu
 * @date : Created on 17:40 2018/5/26
 */
public class OpenIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisTokenManager redisTokenManager;

    public OpenIdHandlerMethodArgumentResolver(RedisTokenManager redisTokenManager) {
        this.redisTokenManager = redisTokenManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class) &&
                methodParameter.hasParameterAnnotation(OpenId.class);
    }

    @Override
    public String resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(Global.TOKEN_KEY);
        System.out.println(token);
        if (token.isEmpty()) {
            return null;
        }
        return redisTokenManager.getId(new TokenModel().setToken(token));
    }
}