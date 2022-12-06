package com.yly.bean.convert.mapstruct.unmappedproperties.mapper;


import com.yly.bean.convert.mapstruct.unmappedproperties.dto.CarDTO;
import com.yly.bean.convert.mapstruct.unmappedproperties.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO carToCarDTO(Car car);
}