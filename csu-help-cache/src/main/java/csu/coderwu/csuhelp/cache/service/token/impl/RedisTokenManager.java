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
    public TokenModel generateToken(TokenModel tokenModel) {
        String token;
        if (tokenModel != null && (token = tokenModel.getToken()) != null && !token.isEmpty()) {
            deleteToken(tokenModel);
            return createToken(redisTemplate.boundValueOps(token).get());
        }
        return null;
    }

    @Override
    public TokenModel createToken(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime updateTime = LocalDateTime.now();
        LocalDateTime expiresTime = updateTime.plusHours(Token.TOKEN_EXPIRES_HOUR);
        TokenModel tokenModel = new TokenModel().setId(id)
                .setToken(token).setUpdateTime(updateTime).setExpireTime(expiresTime);
        redisTemplate.boundValueOps(token).set(id, Token.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if (model == null) {
            return false;
        }
        String id = redisTemplate.boundValueOps(model.getToken()).get();
        return model.getId() != null && id.equals(model.getId());
    }

    @Override
    public void deleteToken(TokenModel tokenModel) {
        if (tokenModel != null) {
            redisTemplate.delete(tokenModel.getToken());
        }
    }

    @Override
    public String getId(TokenModel tokenModel) {
        if (tokenModel != null) {
            return redisTemplate.boundValueOps(tokenModel.getToken()).get();
        }
        return null;
    }
}
