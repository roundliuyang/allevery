package com.yly.bean.convert.mapstruct.unmappedproperties.mapper;


import com.yly.bean.convert.mapstruct.unmappedproperties.dto.DocumentDTO;
import com.yly.bean.convert.mapstruct.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = IgnoreUnmappedMapperConfig.class)
public interface DocumentMapperWithConfig {

    DocumentMapperWithConfig INSTANCE = Mappers.getMapper(DocumentMapperWithConfig.class);

    DocumentDTO documentToDocumentDTO(Document entity);

    Document documentDTOToDocument(DocumentDTO dto);
}