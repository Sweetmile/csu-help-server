package csu.coderwu.csuhelp.wx.mini.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : coderWu
 * @date : Created on 15:23 2018/5/27
 */
@ConfigurationProperties(prefix = "csu.es")
public class CsuEsProperties {

    /**
     * 默认学号
     */
    private String xh;
    /**
     * 教务系统密码
     */
    private String pwd;


    public String getXh() {
        return xh;
    }

    public CsuEsProperties setXh(String xh) {
        this.xh = xh;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public CsuEsProperties setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }
}
