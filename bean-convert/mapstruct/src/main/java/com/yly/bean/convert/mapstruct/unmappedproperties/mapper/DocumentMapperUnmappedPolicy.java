package com.yly.bean.convert.mapstruct.unmappedproperties.mapper;


import com.yly.bean.convert.mapstruct.unmappedproperties.dto.DocumentDTO;
import com.yly.bean.convert.mapstruct.unmappedproperties.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/*
    we can specify the unmapped target policy.
    To do this, we use the MapStruct unmappedTargetPolicy to provide our desired behavior when there is no source field for the mapping:

    ERROR: any unmapped target property will fail the build – this can help us avoid accidentally unmapped fields
    WARN: (default) warning messages during the build
    IGNORE: no output or errors
    In order to ignore unmapped properties and get no output warnings, we should assign the IGNORE value to the unmappedTargetPolicy.

    There are several ways to do it depending on the purpose.(此类是属于1，DocumentMapperWithConfig类 属于2)
       1.Set a Policy on Each Mapper(We can set the unmappedTargetPolicy to the @Mapper annotation. As a result, all its methods will ignore unmapped properties:)
       2.Use a Shared MapperConfig(We can ignore unmapped properties in several mappers by setting the unmappedTargetPolicy via @MapperConfig to share a setting across several mappers.)
       3.Configuration Options(Finally, we can configure the MapStruct code generator's annotation processor options.
         When using Maven, we can pass processor options using the compilerArgs parameter of the processor plug-in:)
           <compilerArgs>
                <compilerArg>
                    -Amapstruct.unmappedTargetPolicy=IGNORE
                </compilerArg>
            </compilerArgs>


    The Order of Precedence（优先级顺序）
    We've looked at several ways that can help us to handle partial mappings and completely ignore unmapped properties.
    We've also seen how to apply them independently on a mapper, but we can also combine them.
    Let's suppose we have a large codebase of beans and mappers with the default MapStruct configuration.
    We don't want to allow partial mappings except in a few cases. We might easily add more fields to a bean
    or its mapped counterpart and get a partial mapping without even noticing it.

    So, it's probably a good idea to add a global setting through Maven configuration to make the build fail in case of partial mappings.

    Now, in order to allow unmapped properties in some of our mappers and override the global behavior, we can combine the techniques, keeping in mind the order of precedence (from highest to lowest):
        Ignoring specific fields at the mapper method-level
        The policy on the mapper
        The shared MapperConfig
        The global configuration
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapperUnmappedPolicy {

    DocumentMapperUnmappedPolicy INSTANCE = Mappers.getMapper(DocumentMapperUnmappedPolicy.class);

    DocumentDTO documentToDocumentDTO(Document entity);

    Document documentDTOToDocument(DocumentDTO dto);
}