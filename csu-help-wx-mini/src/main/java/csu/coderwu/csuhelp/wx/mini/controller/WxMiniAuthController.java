package csu.coderwu.csuhelp.wx.mini.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import csu.coderwu.csuhelp.cache.service.token.impl.RedisTokenManager;
import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.util.ResponseUtil;
import csu.coderwu.csuhelp.db.entity.WxMiniUser;
import csu.coderwu.csuhelp.db.service.WxMiniUserService;
import csu.coderwu.csuhelp.wx.mini.dto.UserInfo;
import csu.coderwu.csuhelp.wx.mini.dto.WxLoginInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : coderWu
 * @date : Created on 15:55 2018/5/26
 */
@RestController
@RequestMapping("/mini/auth")
public class WxMiniAuthController {

    @Autowired
    private WxMiniUserService wxMiniUserService;

    @Autowired
    private RedisTokenManager redisTokenManager;

    @Autowired
    private WxMaService wxMaService;

    @PostMapping("/login")
    public Response miniLogin(@RequestBody WxLoginInfo wxLoginInfo) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxMaService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        if(sessionKey == null || openId == null){
            return ResponseUtil.fail();
        }
        WxMiniUser wxMiniUser = new WxMiniUser();
        wxMiniUser.setAvatarUrl(userInfo.getAvatarUrl());
        wxMiniUser.setCity(userInfo.getCity());
        wxMiniUser.setCountry(userInfo.getCountry());
        wxMiniUser.setGender(userInfo.getGender().intValue());
        wxMiniUser.setNickname(userInfo.getNickName());
        wxMiniUser.setProvince(userInfo.getProvince());
        wxMiniUser.setOpenId(openId);
        wxMiniUserService.addUser(wxMiniUser);
        Map<String, Object> result = new HashMap<>();
        result.put("token", redisTokenManager.createToken(openId).setId(null));
        result.put("userInfo", userInfo);
        return ResponseUtil.success(result);
    }

}
