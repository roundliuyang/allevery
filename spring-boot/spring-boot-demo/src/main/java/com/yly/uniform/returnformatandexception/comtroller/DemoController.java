package com.yly.uniform.returnformatandexception.comtroller;

import com.yly.uniform.returnformatandexception.model.Student;

import com.yly.uniform.returnformatandexception.result.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/hello")
    public ResultData<String> getStr(){
        return ResultData.success("hello,javadaily");
    }

    @GetMapping("/student")
    private Student getStudent(){
        return new Student(1,"liuyang.yuan");
    }
    @GetMapping("/e")
    public int error(){
        System.out.println("ll--------------------------------");
        int i = 9/0;
        return i;
    }
    @GetMapping("/wrong")
    public int wrong(){
        int i;
        try{
            i = 9/0;
        }catch (Exception e){
            System.out.println("1");
            i = 0;
        }
        return i;
    }

    @GetMapping("error1")
    public void empty(){
        throw  new RuntimeException("自定义异常");
    }

}
