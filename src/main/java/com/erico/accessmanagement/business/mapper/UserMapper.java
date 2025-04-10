package com.erico.accessmanagement.business.mapper;

import com.erico.accessmanagement.business.dto.NewUserDto;
import com.erico.accessmanagement.business.model.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "approved", ignore = true)
    })
    User mapToEntity(NewUserDto newUserDto);
}
