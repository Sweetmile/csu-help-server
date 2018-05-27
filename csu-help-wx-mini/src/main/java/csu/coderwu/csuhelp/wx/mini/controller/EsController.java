package csu.coderwu.csuhelp.wx.mini.controller;

import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.util.ResponseUtil;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.db.service.StudentService;
import csu.coderwu.csuhelp.wx.mini.annotation.OpenId;
import csu.coderwu.tool.es.api.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : coderWu
 * @date : Created on 15:06 2018/5/27
 */
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    EsService esService;
    @Autowired
    StudentService studentService;

    @PostMapping("/bind")
    public Response bind(@OpenId String openid,
                         @RequestParam(value = "xh", required = false) String xh,
                         @RequestParam(value = "pwd", required = false) String pwd) {
        if (openid == null) {
            return ResponseUtil.unlogin();
        }
        if (xh == null || pwd == null || xh.isEmpty() || pwd.isEmpty()) {
            return ResponseUtil.badArgument();
        }
        if (!esService.accessible()) {
            return ResponseUtil.fail("教务系统暂时不可用");
        }
        if (esService.loginCheck(xh, pwd)) {
            return ResponseUtil.fail("学号密码不匹配");
        } else {
            Student student = new Student();
            student.setEsPwd(pwd);
            student.setOpenId(openid);
            student.setSchoolNum(xh);
            studentService.addStudent(student);
            return ResponseUtil.success("绑定成功");
        }
    }

}
