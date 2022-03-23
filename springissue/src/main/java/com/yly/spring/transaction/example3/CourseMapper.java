package com.yly.spring.transaction.example3;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CourseMapper {
    @Update("update `course` set number = number + 1 where id = #{id}")
    void addCourseNumber(int courseId);
}
