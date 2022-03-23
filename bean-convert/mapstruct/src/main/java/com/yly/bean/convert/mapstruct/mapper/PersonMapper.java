package com.yly.bean.convert.mapstruct.mapper;


import com.yly.bean.convert.mapstruct.dto.PersonDTO;
import com.yly.bean.convert.mapstruct.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * defaultExpression
 * 从版本 1.3.0 开始，我们可以使用@Mapping注解的defaultExpression属性来指定一个表达式，该表达式在源字段为null时确定目标字段的值。这是对现有defaultValue属性功能的补充。
 *
 * 如果源实体的id字段为null，我们希望生成一个随机id并将其分配给目标，保持其他属性值不变：
 */
@Mapper
public interface PersonMapper {
    
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    
    @Mapping(target = "id", source = "person.id", defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    PersonDTO personToPersonDTO(Person person);
}
