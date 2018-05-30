package csu.coderwu.csuhelp.wx.mini.controller;

import csu.coderwu.csuhelp.core.bean.Response;
import csu.coderwu.csuhelp.core.util.ResponseUtil;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.db.service.StudentService;
import csu.coderwu.csuhelp.wx.mini.annotation.Authorization;
import csu.coderwu.csuhelp.wx.mini.annotation.LoginStudent;
import csu.coderwu.csuhelp.wx.mini.annotation.OpenId;
import csu.coderwu.tool.es.api.EsService;
import csu.coderwu.tool.es.bean.CourseSchedule;
import csu.coderwu.tool.es.bean.SchoolDate;
import csu.coderwu.tool.es.bean.Score;
import csu.coderwu.tool.es.exception.EsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Authorization
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

    @Authorization
    @GetMapping("/unbind")
    public Response unbind(@OpenId String openid) {
        studentService.removeStudent(openid);
        return ResponseUtil.success("取消绑定成功");
    }

    @GetMapping("/info")
    @Authorization
    public Response getInfo(@LoginStudent Student student) {
        if (student == null) {
            return ResponseUtil.fail("还没绑定");
        }
        try {
            esService.loginCheck(student.getSchoolNum(), student.getEsPwd());
            student.setEsPwd(null);
            student.setOpenId(null);
            return ResponseUtil.success(student);
        } catch (EsException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @GetMapping("/schedule")
    @Authorization
    public Response getSchedule(@LoginStudent Student student,
                                @RequestParam(value = "week", required = false) Integer week) {
        if (student == null) {
            return ResponseUtil.fail("还没绑定教务系统哦");
        }
        String xh = student.getSchoolNum();
        String pwd = student.getEsPwd();
        try {
            esService.loginCheck(xh, pwd);
            SchoolDate schoolDate = esService.getSchoolData();
            CourseSchedule courseSchedule = esService.getCourseSchedule(
                    xh, pwd, schoolDate.getSemester(),
                    week == null ? schoolDate.getWeekNumber() : week);
            return ResponseUtil.success(courseSchedule);
        } catch (EsException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @GetMapping("/grades")
    @Authorization
    public Response getGrades(@LoginStudent Student student,
                              @RequestParam(value = "semester", required = false) String semester) {
        if (student == null) {
            return ResponseUtil.fail("还没绑定教务系统哦");
        }
        String xh = student.getSchoolNum();
        String pwd = student.getEsPwd();
        try {
            esService.loginCheck(xh, pwd);
            SchoolDate schoolDate = esService.getSchoolData();
            semester = semester == null ? schoolDate.getSemester() : semester;
            List<Score> scoreList = esService.getScore(xh, pwd, semester);
            return ResponseUtil.success(scoreList);
        } catch (EsException e) {
            return ResponseUtil.fail(e.getMessage());
        }
    }
}
