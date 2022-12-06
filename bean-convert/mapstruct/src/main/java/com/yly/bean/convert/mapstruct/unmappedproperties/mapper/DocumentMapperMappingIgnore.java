package com.yly.bean.convert.mapstruct.unmappedproperties.mapper;


import com.yly.bean.convert.mapstruct.unmappedproperties.dto.DocumentDTO;
import com.yly.bean.convert.mapstruct.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 警告没了
 * Here, we've provided the field name as the target and set ignore to true to show that it's not required for mapping.
 *
 * However, this technique is not convenient for some cases. We may find it difficult to use, for example,
 * when using big models with a large number of fields.
 */
@Mapper
public interface DocumentMapperMappingIgnore {

    DocumentMapperMappingIgnore INSTANCE = Mappers.getMapper(DocumentMapperMappingIgnore.class);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "author", ignore = true)
    DocumentDTO documentToDocumentDTO(Document entity);

    @Mapping(target = "modificationTime", ignore = true)
    Document documentDTOToDocument(DocumentDTO dto);

}