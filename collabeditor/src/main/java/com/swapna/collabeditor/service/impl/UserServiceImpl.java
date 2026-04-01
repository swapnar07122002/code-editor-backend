package com.swapna.collabeditor.service.impl;

import com.swapna.collabeditor.dto.UserDto;
import com.swapna.collabeditor.entity.User;
import com.swapna.collabeditor.exception.ResourceNotFoundException;
import com.swapna.collabeditor.mapper.UserMapper;
import com.swapna.collabeditor.repository.UserRepository;
import com.swapna.collabeditor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        //check if email already exists
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // convert user dto to user entity
        User user = UserMapper.mapToUser(userDto);

        // saving user entity into database
        User savedUser = userRepository.save(user);

        // convert saved user to user dto
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(("User does not exist with given id: "+ userId)));

        // convert user to user dto
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto updatedUser) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(("User does not exist with given id: "+ userId)));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setProfileImage(updatedUser.getProfileImage());

        User updatedUserObj = userRepository.save(user);
        return UserMapper.mapToUserDto(updatedUserObj);
    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(("User does not exist with given id: "+ userId)));

        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        // convert user to dto
        return UserMapper.mapToUserDto(user);
    }

}
