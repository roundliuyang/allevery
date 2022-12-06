package com.yly.bean.convert.mapstruct.unmappedproperties.mapper;


import com.yly.bean.convert.mapstruct.unmappedproperties.dto.DocumentDTO;
import com.yly.bean.convert.mapstruct.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *  unmappedSourcePolicy() default ReportingPolicy.IGNORE;
 *  ReportingPolicy unmappedTargetPolicy() default ReportingPolicy.WARN;
 *
 *  As MapStruct operates at compile time, it can be faster than a dynamic mapping framework.
 *  It can also generate error reports if mappings are incomplete — that is, if not all target properties are mapped:
 *    Warning:(X,X) java: Unmapped target property: "propertyName".这句是重点，重点，重点
 *  While this is is a helpful warning in the case of an accident, we may prefer to handle things differently if the fields are missing on purpose.
 *  Let's explore this with an example of mapping two simple objects:
 *  简而言之，编译时会发生警告
 */
@Mapper
public interface DocumentMapper {

    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    DocumentDTO documentToDocumentDTO(Document entity);

    Document documentDTOToDocument(DocumentDTO dto);
}