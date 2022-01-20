package com.yly.mapper;


import com.yly.bean.convert.mapstruct.dto.SimpleSource;
import com.yly.bean.convert.mapstruct.entity.SimpleDestination;
import com.yly.bean.convert.mapstruct.mapper.SimpleDestinationMapperUsingInjectedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SimpleDestinationMapperUsingInjectedIntegrationTest {

    @Autowired
    private SimpleDestinationMapperUsingInjectedService mapper;

    /**
     * 依赖注入映射
     */
    @Test
    public void givenSourceToDestination_whenMaps_thenNameEnriched() {
        // Given
        SimpleSource source = new SimpleSource();
        source.setName("Bob");
        source.setDescription("The Builder");

        // When
        SimpleDestination destination = mapper.sourceToDestination(source);

        // Then
        assertThat(destination).isNotNull();
        assertThat(destination.getName()).isEqualTo("-:: Bob ::-");
        assertThat(destination.getDescription()).isEqualTo("The Builder");
    }
}