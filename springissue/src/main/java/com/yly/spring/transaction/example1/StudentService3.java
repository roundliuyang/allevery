package com.yly.spring.transaction.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StudentService3 {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService3 studentService;

    public void saveStudent(String realname) throws Exception {
        Student student = new Student();
        student.setRealname(realname);
        studentService.doSaveStudent(student);
    }

    @Transactional
    private void doSaveStudent(Student student) throws Exception {
        studentMapper.saveStudent(student);
        if (student.getRealname().equals("小明")) {
            throw new RuntimeException("该用户已存在");
        }
    }
}