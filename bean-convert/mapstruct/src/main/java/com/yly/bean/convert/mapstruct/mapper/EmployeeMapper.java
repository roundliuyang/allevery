package com.yly.bean.convert.mapstruct.mapper;

import com.yly.bean.convert.mapstruct.dto.DivisionDTO;
import com.yly.bean.convert.mapstruct.dto.EmployeeDTO;
import com.yly.bean.convert.mapstruct.entity.Division;
import com.yly.bean.convert.mapstruct.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;



@Mapper
public interface EmployeeMapper {

    /**
     * MapStruct 还提供了一些现成的隐式类型转换，对于我们的示例，我们将尝试将 String 日期转换为实际的Date对象。
     */
    @Mappings({
            @Mapping(target="employeeId", source = "entity.id"),
            @Mapping(target="employeeName", source = "entity.name"),
            @Mapping(target="employeeStartDt", source = "entity.startDt",
                    dateFormat = "dd-MM-yyyy HH:mm:ss")})
    EmployeeDTO employeeToEmployeeDTO(Employee entity);
    @Mappings({
            @Mapping(target="id", source="dto.employeeId"),
            @Mapping(target="name", source="dto.employeeName"),
            @Mapping(target="startDt", source="dto.employeeStartDt",
                    dateFormat="dd-MM-yyyy HH:mm:ss")})
    Employee employeeDTOtoEmployee(EmployeeDTO dto);

    /**
     * 这里我们需要添加一个方法，将Division转换为DivisionDTO，反之亦然；如果 MapStruct 检测到需要转换的对象类型且转换的方法存在于同一个类中，它会自动使用它。
     * 让我们将它添加到mapper中：
     */
    DivisionDTO divisionToDivisionDTO(Division entity);

    Division divisionDTOtoDivision(DivisionDTO dto);


}