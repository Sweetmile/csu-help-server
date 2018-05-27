package csu.coderwu.csuhelp.db.service;

import csu.coderwu.csuhelp.db.dao.WxMiniUserMapper;
import csu.coderwu.csuhelp.db.entity.WxMiniUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : coderWu
 * @date : Created on 21:36 2018/5/26
 */
@Service
public class WxMiniUserService {

    @Autowired
    WxMiniUserMapper wxMiniUserMapper;

    public void addUser(WxMiniUser wxMiniUser) {
        if (wxMiniUser == null) {
            return;
        }
        WxMiniUser user = wxMiniUserMapper.selectByPrimaryKey(wxMiniUser.getOpenId());
        if (user != null) {
            wxMiniUserMapper.updateByPrimaryKey(wxMiniUser);
        } else {
            wxMiniUserMapper.insert(wxMiniUser);
        }
    }

}
