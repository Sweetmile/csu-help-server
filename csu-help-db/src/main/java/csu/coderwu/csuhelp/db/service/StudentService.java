package csu.coderwu.csuhelp.db.service;

import csu.coderwu.csuhelp.db.dao.StudentMapper;
import csu.coderwu.csuhelp.db.entity.Student;
import csu.coderwu.csuhelp.db.entity.StudentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : coderWu
 * @date : Created on 15:50 2018/5/27
 */
@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    public void addStudent(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andOpenIdEqualTo(student.getOpenId());
        List<Student> students = studentMapper.selectByExample(studentExample);
        if (students.size() <= 0) {
            studentMapper.insert(student);
        } else {
            student.setId(students.get(0).getId());
            studentMapper.updateByPrimaryKey(student);
        }
    }

    public Student getStudent(String openid) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andOpenIdEqualTo(openid);
        List<Student> students = studentMapper.selectByExample(studentExample);
        return students.size() > 0 ? students.get(0) : null;
    }

    public void removeStudent(String openid) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andOpenIdEqualTo(openid);
        studentMapper.deleteByExample(studentExample);
    }

}
