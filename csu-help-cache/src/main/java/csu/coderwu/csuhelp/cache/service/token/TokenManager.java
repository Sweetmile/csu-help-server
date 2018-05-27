package csu.coderwu.csuhelp.cache.service.token;

import csu.coderwu.csuhelp.cache.bean.TokenModel;

/**
 * @author : coderWu
 * @date : Created on 17:34 2018/5/27
 */
public interface TokenManager {

    TokenModel generateToken(TokenModel tokenModel);

    TokenModel createToken(String id);

    boolean checkToken(TokenModel model);

    void deleteToken(TokenModel tokenModel);

    String getId(TokenModel tokenModel);

}
