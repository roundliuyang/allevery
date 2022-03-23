package com.yly.mapper;


import com.yly.bean.convert.mapstruct.dto.SimpleSource;
import com.yly.bean.convert.mapstruct.entity.SimpleDestination;
import com.yly.bean.convert.mapstruct.mapper.SimpleSourceDestinationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * 基本映射
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SimpleSourceDestinationMapperIntegrationTest {

    @Autowired
    SimpleSourceDestinationMapper simpleSourceDestinationMapper;

    /**
     * 基本映射
     */
    @Test
    public void givenSourceToDestination_whenMaps_thenCorrect() {
        SimpleSource simpleSource = new SimpleSource();
        simpleSource.setName("SourceName");
        simpleSource.setDescription("SourceDescription");

        SimpleDestination destination = simpleSourceDestinationMapper.sourceToDestination(simpleSource);

        assertEquals(simpleSource.getName(), destination.getName());
        assertEquals(simpleSource.getDescription(), destination.getDescription());
        System.out.println("对的");
    }

    @Test
    public void givenDestinationToSource_whenMaps_thenCorrect() {
        SimpleDestination destination = new SimpleDestination();
        destination.setName("DestinationName");
        destination.setDescription("DestinationDescription");

        SimpleSource source = simpleSourceDestinationMapper.destinationToSource(destination);

        assertEquals(destination.getName(), source.getName());
        assertEquals(destination.getDescription(), source.getDescription());
    }

}
