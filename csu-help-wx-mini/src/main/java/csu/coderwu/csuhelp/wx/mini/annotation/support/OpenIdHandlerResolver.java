package csu.coderwu.csuhelp.wx.mini.annotation.support;

import csu.coderwu.csuhelp.wx.mini.annotation.OpenId;
import csu.coderwu.csuhelp.wx.mini.config.Constants;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author : coderWu
 * @date : Created on 17:40 2018/5/26
 */
public class OpenIdHandlerResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(String.class) &&
                methodParameter.hasParameterAnnotation(OpenId.class);
    }

    @Override
    public String resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return (String) nativeWebRequest.getAttribute(Constants.INNER_OPENID, RequestAttributes.SCOPE_REQUEST);

    }
}
