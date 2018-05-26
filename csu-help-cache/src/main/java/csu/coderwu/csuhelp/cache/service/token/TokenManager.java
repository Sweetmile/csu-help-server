package csu.coderwu.csuhelp.cache.service.token;

import csu.coderwu.csuhelp.cache.bean.TokenModel;

/**
 * @author : coderWu
 * @date : Created on 16:38 2018/5/26
 */
public interface TokenManager {


    /**
     * 更新token
     * @param id 标识用户的id
     * @return TokenModel
     */
    TokenModel generateToken(String id);

    /**
     * 创建token
     * @param id 标识用户的id
     * @return TokenModel
     */
    TokenModel createToken (String id);

    /**
     * 检查token是否过期
     * @param model TokenModel
     * @return boolean
     */
    boolean checkToken (TokenModel model);

    /**
     * 删除token
     * @param id 标识用户的id
     */
    void deleteToken (String id);
}
