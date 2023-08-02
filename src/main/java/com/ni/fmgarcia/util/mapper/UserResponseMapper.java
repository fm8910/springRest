package com.ni.fmgarcia.util.mapper;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {

    UserResponse userEntityToUserResponse(User user);
}
