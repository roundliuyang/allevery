package com.yly.bean.convert.mapstruct.mapper;


import com.yly.bean.convert.mapstruct.dto.SimpleSource;
import com.yly.bean.convert.mapstruct.entity.SimpleDestination;
import org.mapstruct.Mapper;

/**
 * 请注意，我们没有为 SimpleSourceDestinationMapper 创建一个实现类——因为 MapStruct 为我们创建了它
 * 我们可以通过执行mvn clean install来触发 MapStruct 处理。
 * 这将在/target/generated-sources/annotations/下生成实现类。
 * 这是 MapStruct 为我们自动创建的类：
 */
@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {

    SimpleDestination sourceToDestination(SimpleSource source);

    SimpleSource destinationToSource(SimpleDestination destination);

}
