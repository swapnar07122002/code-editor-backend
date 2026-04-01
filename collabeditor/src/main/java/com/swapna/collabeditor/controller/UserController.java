package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.UserDto;
import com.swapna.collabeditor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;


    // Build Get User REST API
    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long userId) {
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!loggedInUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Unauthorized: Access denied"));
        }

        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

//    // Build Get All Users REST API
//    @GetMapping
//    public ResponseEntity<List<UserDto>> getAllUsers(){
//        List<UserDto> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }

    // Build Update User REST API
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long userId, @RequestBody UserDto updatedUser) {
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if user is allowed to update
        if (!loggedInUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Unauthorized: Access denied"));
        }
        UserDto userDto = userService.updateUser(userId,updatedUser);
        return ResponseEntity.ok(userDto);
    }

    // Build Delete User REST API
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        UserDto loggedInUser = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Only allow deleting own account
        if (!loggedInUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Unauthorized: Access denied"));
        }
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

}
