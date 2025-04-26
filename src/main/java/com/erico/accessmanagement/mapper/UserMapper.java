package com.erico.accessmanagement.mapper;

import com.erico.accessmanagement.dto.CreateUserDto;
import com.erico.accessmanagement.model.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

//    @Mappings(value = {
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "role", ignore = true),
//            @Mapping(target = "approved", ignore = true),
//            @Mapping(target = "status", ignore = true)
//    })
    User mapToEntity(CreateUserDto createUserDto);
}
