package csu.coderwu.csuhelp.wx.mini.controller;

import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.util.ResponseUtil;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.db.service.StudentService;
import csu.coderwu.csuhelp.wx.mini.annotation.Authorization;
import csu.coderwu.csuhelp.wx.mini.annotation.LoginStudent;
import csu.coderwu.csuhelp.wx.mini.annotation.OpenId;
import csu.coderwu.tool.es.api.EsService;
import csu.coderwu.tool.es.exception.EsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public Response bind(@OpenId String openid, @RequestBody Map<String, Object> params) {
        String xh = (String) params.get("xh");
        String pwd = (String) params.get("pwd");
        if (openid == null) {
            return ResponseUtil.unlogin();
        }
        if (xh == null || pwd == null || xh.isEmpty() || pwd.isEmpty()) {
            return ResponseUtil.badArgument();
        }
        try {
            esService.accessible();
            esService.loginCheck(xh, pwd);
            Student student = new Student();
            student.setEsPwd(pwd);
            student.setOpenId(openid);
            student.setSchoolNum(xh);
            student.setName(esService.getStudentInfo(xh, pwd).getName());
            studentService.addStudent(student);
            return ResponseUtil.success("绑定成功");
        } catch (EsException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @GetMapping("/info")
    @Authorization
    public Response getInfo(@LoginStudent Student student) {
        try {
            esService.loginCheck(student.getSchoolNum(), student.getEsPwd());
            student.setEsPwd(null);
            return ResponseUtil.success(student);
        } catch (EsException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }

    //TODO 等tool es 加入获取教学日期
//    @GetMapping("/schedule")
//    @Authorization
//    public Response getSchedule(@LoginStudent Student student) {
//        try {
//            esService.loginCheck(student.getSchoolNum(), student.getEsPwd());
//
//        }
//    }

}
