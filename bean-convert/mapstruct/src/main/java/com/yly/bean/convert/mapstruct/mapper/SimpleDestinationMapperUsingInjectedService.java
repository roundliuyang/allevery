package com.yly.bean.convert.mapstruct.mapper;


import com.yly.bean.convert.mapstruct.dto.SimpleSource;
import com.yly.bean.convert.mapstruct.entity.SimpleDestination;
import com.yly.bean.convert.mapstruct.service.SimpleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SimpleDestinationMapperUsingInjectedService {

    @Autowired
    protected SimpleService simpleService;

    @Mapping(target = "name", expression = "java(simpleService.enrichName(source.getName()))")
    public abstract SimpleDestination sourceToDestination(SimpleSource source);

    public abstract SimpleSource destinationToSource(SimpleDestination destination);


}
