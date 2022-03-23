package com.yly.mapper;


import com.yly.bean.convert.mapstruct.dto.PersonDTO;
import com.yly.bean.convert.mapstruct.entity.Person;
import com.yly.bean.convert.mapstruct.mapper.PersonMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonMapperUnitTest {

    @Test
    public void givenPersonEntitytoPersonWithExpression_whenMaps_thenCorrect() {

        Person entity  = new Person();
        entity.setName("Micheal");

        PersonDTO personDto = PersonMapper.INSTANCE.personToPersonDTO(entity);

        assertNull(entity.getId());
        assertNotNull(personDto.getId());
        assertEquals(personDto.getName(), entity.getName());
    }
}