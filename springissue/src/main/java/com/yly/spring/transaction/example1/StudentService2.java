package com.yly.spring.transaction.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author: yly
 * Date: 2021/10/21 12:54
 */

@Service
public class StudentService2 {
    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    public void saveStudent(String realname) throws Exception {
        Student student = new Student();
        student.setRealname(realname);
        studentMapper.saveStudent(student);
        if (student.getRealname().equals("小明")) {
            throw new RuntimeException("该用户已存在");
        }
    }
}
