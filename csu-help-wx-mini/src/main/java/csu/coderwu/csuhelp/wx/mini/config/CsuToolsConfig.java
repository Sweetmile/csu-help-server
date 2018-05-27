package csu.coderwu.csuhelp.wx.mini.config;

import csu.coderwu.tool.cet.api.CetService;
import csu.coderwu.tool.cet.api.impl.CetServiceImpl;
import csu.coderwu.tool.es.api.EsService;
import csu.coderwu.tool.es.api.impl.EsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : coderWu
 * @date : Created on 15:27 2018/5/27
 */
@Configuration
@EnableConfigurationProperties({CsuEsProperties.class})
public class CsuToolsConfig {

    @Autowired
    CsuEsProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public EsService esService() {
        return new EsServiceImpl(properties.getXh(), properties.getPwd());
    }

    @Bean
    @ConditionalOnMissingBean
    public CetService cetService() {
        return new CetServiceImpl();
    }

}
