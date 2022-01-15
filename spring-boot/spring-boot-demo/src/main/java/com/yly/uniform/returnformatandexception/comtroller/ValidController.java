package com.yly.uniform.returnformatandexception.comtroller;

import com.yly.uniform.returnformatandexception.vo.ValidGroup;
import com.yly.uniform.returnformatandexception.vo.ValidVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.groups.Default;

/**
 * 参数校验示例
 * @author jam
 * @date 2021/8/4 4:05 下午
 */
@RestController
@Api(tags = "参数校验")
@Slf4j
@Validated
public class ValidController {

    @PostMapping("/valid/test1")
    @ApiOperation("RequestBody校验")
    public String test1(@Validated @RequestBody ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test1 valid success";
    }

    @ApiOperation("Form校验")
    @PostMapping(value = "/valid/test2")
    public String test2(@Validated ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test2 valid success";
    }

    @ApiOperation("单参数校验")
    @PostMapping(value = "/valid/test3")
    public String test3(@Email String email){
        log.info("email is {}", email);
        return "email valid success";
    }


    @ApiOperation("新增")
    @PostMapping(value = "/valid/add")
    public String add(@Validated(value = {ValidGroup.Crud.Create.class,Default.class}) ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test3 valid success";
    }


    @ApiOperation("更新")
    @PostMapping(value = "/valid/update")
    public String update(@Validated(value = ValidGroup.Crud.Update.class) ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test4 valid success";
    }

    @ApiOperation("test5")
    @PostMapping(value = "/valid/test5")
    public String test5(@Validated(value = {ValidGroup.Crud.Update.class, Default.class}) ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test5 valid success";
    }


    @ApiOperation("test6")
    @PostMapping(value = "/valid/test6")
    public String test6(@Validated(value = {ValidGroup.Crud.Update.class,ValidGroup.Crud.Create.class}) ValidVO validVO){
//        log.info("validEntity is {}", validVO);
        return "test6 valid success";
    }

}
