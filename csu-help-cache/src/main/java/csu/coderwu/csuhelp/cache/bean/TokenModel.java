package csu.coderwu.csuhelp.cache.bean;

import java.time.LocalDateTime;

/**
 *
 * 生成token用的模型
 * @author : coderWu
 * @date : Created on 16:36 2018/5/26
 */
public class TokenModel {

    private String id;
    private String token;
    private LocalDateTime expireTime;
    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public TokenModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public TokenModel setToken(String token) {
        this.token = token;
        return this;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public TokenModel setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public TokenModel setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
