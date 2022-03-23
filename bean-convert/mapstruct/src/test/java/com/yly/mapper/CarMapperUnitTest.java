package com.yly.mapper;


import com.yly.bean.convert.mapstruct.dto.CarDTO;
import com.yly.bean.convert.mapstruct.entity.Car;
import com.yly.bean.convert.mapstruct.mapper.CarMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarMapperUnitTest {

    @Test
    public void givenCarEntitytoCar_whenMaps_thenCorrect() {
        
        Car entity  = new Car();
        entity.setId(1);
        entity.setName("Toyota");
        
        CarDTO carDto = CarMapper.INSTANCE.carToCarDTO(entity);

        assertEquals(carDto.getId(), entity.getId());
        assertEquals(carDto.getName(), entity.getName());
    }
}
