package csu.coderwu.csuhelp.cache.service.token.impl;

import csu.coderwu.csuhelp.cache.bean.TokenModel;
import csu.coderwu.csuhelp.cache.constant.Token;
import csu.coderwu.csuhelp.cache.service.token.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : coderWu
 * @date : Created on 16:42 2018/5/26
 */
@Component
public class RedisTokenManager implements TokenManager {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public TokenModel generateToken(String id) {
        TokenModel tokenModel = new TokenModel();        tokenModel.setId(id);
        tokenModel.setToken(redisTemplate.boundValueOps(id).get());
        LocalDateTime updateTime = LocalDateTime.now();
        LocalDateTime expiresTime = updateTime.plusHours(Token.TOKEN_EXPIRES_HOUR);
        return createToken(id);
    }

    @Override
    public TokenModel createToken(String id) {
        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime updateTime = LocalDateTime.now();
        LocalDateTime expiresTime = updateTime.plusHours(Token.TOKEN_EXPIRES_HOUR);
        TokenModel tokenModel = new TokenModel().setId(id)
                .setToken(token).setUpdateTime(updateTime).setExpireTime(expiresTime);
        redisTemplate.boundValueOps(id).set(token, Token.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String token = redisTemplate.boundValueOps(model.getId()).get();
        return token.equals(model.getToken());
    }

    @Override
    public void deleteToken(String id) {
        redisTemplate.delete(id);
    }
}
