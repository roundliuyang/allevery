package com.yly.mapper;

import com.yly.bean.convert.mapstruct.dto.DivisionDTO;
import com.yly.bean.convert.mapstruct.dto.EmployeeDTO;
import com.yly.bean.convert.mapstruct.entity.Employee;
import com.yly.bean.convert.mapstruct.mapper.EmployeeMapper;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class EmployeeMapperUnitTest {

    EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    /**
     * 映射不同字段名称的字段
     */
    @Test
    public void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(1);
        dto.setEmployeeName("John");

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
    }


    /**
     * 测试用子 Bean 映射 Bean
     */
    @Test
    public void givenEmpDTONestedMappingToEmp_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setDivision(new DivisionDTO(1, "Division1"));

        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }


    /**
     *  类型转换的映射
     *  例如 Date与日期字符串
     */
    @Test
    public void givenEmpStartDtMappingToEmpDTO_whenMaps_thenCorrect() throws ParseException, ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());
        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
                entity.getStartDt().toString());
    }
    @Test
    public void givenEmpDTOStartDtMappingToEmp_whenMaps_thenCorrect() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("01-04-2016 01:00:00");
        Employee entity = mapper.employeeDTOtoEmployee(dto);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
                entity.getStartDt().toString());
    }
}
