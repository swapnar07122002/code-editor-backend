package com.swapna.collabeditor.service;

import com.swapna.collabeditor.dto.UserDto;
import com.swapna.collabeditor.mapper.UserMapper;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto updatedUser);

    void deleteUser(Long userId);

    UserDto findByEmail(String email);
}
