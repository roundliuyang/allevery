package com.yly.mapper;


import com.yly.bean.convert.mapstruct.dto.CarDTO;
import com.yly.bean.convert.mapstruct.dto.FuelType;
import com.yly.bean.convert.mapstruct.entity.BioDieselCar;
import com.yly.bean.convert.mapstruct.entity.Car;
import com.yly.bean.convert.mapstruct.entity.ElectricCar;
import com.yly.bean.convert.mapstruct.mapper.CarsMapper;
import org.junit.Test;

import org.mapstruct.factory.Mappers;

import static org.junit.Assert.assertEquals;

class CarsMapperUnitTest {

    private CarsMapper sut = Mappers.getMapper(CarsMapper.class);

    @Test
    void testGivenSubTypeElectric_mapsModifiedFieldsToSuperTypeDto_whenBeforeAndAfterMappingMethodscarCalled() {
        Car car = new ElectricCar();
        car.setId(12);
        car.setName("Tesla_Model_C");
        
        CarDTO carDto = sut.toCarDto(car);
        
        assertEquals("TESLA_MODEL_C", carDto.getName());
        assertEquals(FuelType.ELECTRIC, carDto.getFuelType());
    }
    
    @Test
    void testGivenSubTypeBioDiesel_mapsModifiedFieldsToSuperTypeDto_whenBeforeAndAfterMappingMethodscarCalled() {
        Car car = new BioDieselCar();
        car.setId(11);
        car.setName("Tesla_Model_X");
        
        CarDTO carDto = sut.toCarDto(car);
        
        assertEquals("TESLA_MODEL_X", carDto.getName());
        assertEquals(FuelType.BIO_DIESEL, carDto.getFuelType());
    }

}
