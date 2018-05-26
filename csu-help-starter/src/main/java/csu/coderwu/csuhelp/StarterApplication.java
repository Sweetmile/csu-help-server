package csu.coderwu.csuhelp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "csu.coderwu.csuhelp",
        "csu.coderwu.csuhelp.core",
        "csu.coderwu.csuhelp.db",
        "csu.coderwu.csuhelp.cache",
        "csu.coderwu.csuhelp.wx.mini"

})
@MapperScan("csu.coderwu.csuhelp.db.do")
public class StarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
