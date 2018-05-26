package csu.coderwu.csuhelp.cache.service.token;

import csu.coderwu.csuhelp.cache.bean.TokenModel;

/**
 * @author : coderWu
 * @date : Created on 16:38 2018/5/26
 */
public interface TokenManager {


    /**
     * 更新token
     * @param tokenModel TokenModel 现有token
     * @return TokenModel
     */
    TokenModel generateToken(TokenModel tokenModel);

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
     * @param tokenModel 标识用户的id
     */
    void deleteToken (TokenModel tokenModel);

    /**
     * 根据token获取id
     * @param tokenModel TokenModel
     * @return String id
     */
    String getId(TokenModel tokenModel);
}
