package com.yly.bean.convert.mapstruct.mapper;


import com.yly.bean.convert.mapstruct.dto.CarDTO;
import com.yly.bean.convert.mapstruct.dto.FuelType;

import com.yly.bean.convert.mapstruct.entity.BioDieselCar;
import com.yly.bean.convert.mapstruct.entity.Car;

import com.yly.bean.convert.mapstruct.entity.ElectricCar;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class CarsMapper {

    @BeforeMapping
    protected void enrichDTOWithFuelType(Car car, @MappingTarget CarDTO carDto) {
        if (car instanceof ElectricCar)
            carDto.setFuelType(FuelType.ELECTRIC);
        if (car instanceof BioDieselCar)
            carDto.setFuelType(FuelType.BIO_DIESEL);
    }

    @AfterMapping
    protected void convertNameToUpperCase(@MappingTarget CarDTO carDto) {
        carDto.setName(carDto.getName().toUpperCase());
    }

    public abstract CarDTO toCarDto(Car car);

}
