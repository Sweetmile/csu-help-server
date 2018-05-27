package csu.coderwu.csuhelp.core.util;

import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.constant.ResponseCode;


/**
 * @author : coderWu
 * @date : Created on 20:04 2018/5/24
 */
public class ResponseUtil {

    public static Response success() {
        return new Response(ResponseCode.SUCCESS, "success", null);
    }

    public static Response success(Object data) {
        return new Response(ResponseCode.SUCCESS, "success", data);
    }

    public static Response success(String message) {
        return new Response(ResponseCode.SUCCESS, message, null);
    }


    public static Response success(String message, Object data) {
        return new Response(ResponseCode.SUCCESS, message, data);
    }

    public static Response fail() {
        return new Response(ResponseCode.FAIL, "fail", null);
    }

    public static Response fail(Object data) {
        return new Response(ResponseCode.FAIL, "fail", data);
    }

    public static Response fail(String message) {
        return new Response(ResponseCode.FAIL, message, null);
    }

    public static Response fail(String message, Object data) {
        return new Response(ResponseCode.FAIL, message, data);
    }

    public static Response unlogin(){
        return new Response(ResponseCode.NOT_LOGIN, "未登录", null);
    }

    public static Response badArgument(){
        return new Response(ResponseCode.ARGUMENT_ERROR, "参数错误", null);
    }

    public static Response serious(){
        return new Response(ResponseCode.SERVER_ERROR, "服务器错误", null);
    }

}
