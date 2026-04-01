package com.swapna.collabeditor.mapper;

import com.swapna.collabeditor.dto.UserDto;
import com.swapna.collabeditor.entity.User;

public class UserMapper {

    // convert user entity to user dto
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getProfileImage(),
                user.getCreatedAt()
        );
    }

    // convert user dto to user entity
    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(),
                userDto.getProfileImage(),
                userDto.getCreatedAt()
        );
    }
}
