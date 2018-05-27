package csu.coderwu.csuhelp.wx.mini;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={
        "csu.coderwu.csuhelp.core",
        "csu.coderwu.csuhelp.wx.mini",
        "csu.coderwu.csuhelp.db",
        "csu.coderwu.csuhelp.cache"
})
@MapperScan("csu.coderwu.csuhelp.db.dao")
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
