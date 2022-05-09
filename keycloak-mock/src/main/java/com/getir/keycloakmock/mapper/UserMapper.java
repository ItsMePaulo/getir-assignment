package com.getir.keycloakmock.mapper;

import com.getir.keycloakmock.document.User;
import com.getir.keycloakmock.model.UserDto;
import com.getir.keycloakmock.model.UserToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(generateId())"),
            @Mapping(target = "name", source = "username")
    })
    User mapUserDtoToUser(UserDto user);

    @Mappings(
            @Mapping(target = "username", source = "name")
    )
    UserToken mapUserToToken(User savedUser);

    List<UserDto> mapAllUsersToDtos(List<User> users);

    @Mappings(
            @Mapping(target = "username", source = "name")
    )
    UserDto mapUserToUserDto(User user);

    default UUID generateId() {
        return UUID.randomUUID();
    }

}
