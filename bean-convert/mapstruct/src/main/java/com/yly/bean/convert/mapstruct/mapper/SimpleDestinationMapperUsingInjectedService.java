package com.yly.bean.convert.mapstruct.mapper;


import com.yly.bean.convert.mapstruct.dto.SimpleSource;
import com.yly.bean.convert.mapstruct.entity.SimpleDestination;
import com.yly.bean.convert.mapstruct.service.SimpleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 依赖注入映射
 *接下来，让我们通过调用Mappers.getMapper(YourClass.class)在 MapStruct 中获取一个映射器的实例。
 * 当然，这是获取实例的一种非常手动的方式。然而，一个更好的选择是直接在我们需要的地方注入映射器（如果我们的项目使用任何依赖注入解决方案）。
 * 幸运的是，MapStruct 对 Spring 和 CDI （上下文和依赖注入）都有可靠的支持。
 * 要在我们的映射器中使用 Spring IoC，我们需要将componentModel属性添加到@Mapper，其值为spring，对于 CDI，它将是cdi。
 */
@Mapper(componentModel = "spring")
public abstract class SimpleDestinationMapperUsingInjectedService {

    /**
     * 我们必须记住不要将注入的 bean 设为私有！这是因为 MapStruct 必须访问生成的实现类中的对象。
     */
    @Autowired
    protected SimpleService simpleService;

    @Mapping(target = "name", expression = "java(simpleService.enrichName(source.getName()))")
    public abstract SimpleDestination sourceToDestination(SimpleSource source);

    public abstract SimpleSource destinationToSource(SimpleDestination destination);


}
