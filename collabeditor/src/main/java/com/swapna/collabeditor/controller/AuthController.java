package com.swapna.collabeditor.controller;

import com.swapna.collabeditor.dto.UserDto;
import com.swapna.collabeditor.service.EmailService;
import com.swapna.collabeditor.service.UserService;
import com.swapna.collabeditor.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    private JwtUtil jwtUtil;

    private PasswordEncoder passwordEncoder;

    private EmailService emailService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // save user (this may throw RuntimeException if email already exists)
            UserDto savedUser = userService.createUser(userDto);

            // generate jwt
            String token = jwtUtil.generateToken(savedUser.getId());

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully!",
                    "token", token
            ));
        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }


    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {

        try {
            UserDto user = userService.findByEmail(userDto.getEmail());
            if (user == null || !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
            }

            String token = jwtUtil.generateToken(user.getId());

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token
            ));
        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }

    }

    // Forgot Password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check if user exists
        UserDto user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        // Generate JWT reset token
        String token = jwtUtil.generatePasswordResetToken(user.getId());

        // Create frontend link
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        // HTML email body
        String html = """
        <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; padding: 24px; box-shadow: 0 2px 6px rgba(0,0,0,0.1);">
            <div style="text-align: center; margin-bottom: 24px;">
                <h2 style="color: #1a73e8; margin-bottom: 8px;">Password Reset Request</h2>
                <p style="color: #555;">Hi there,</p>
            </div>
            
            <p style="color: #333; line-height: 1.5;">
                You recently requested to reset your password for <b>CollabEditor</b>. 
                Click the button below to reset it. This link will expire in <b>15 minutes</b>.
            </p>
        
            <div style="text-align: center; margin: 30px 0;">
                <a href="%s" style="background-color: #1a73e8; color: #fff; text-decoration: none; padding: 12px 24px; border-radius: 6px; font-weight: bold;">Reset Password</a>
            </div>
        
            <p style="color: #555; line-height: 1.5;">
                If you didn’t request this, you can safely ignore this email.
            </p>
        
            <hr style="border: none; border-top: 1px solid #eee; margin: 24px 0;">
        
            <p style="color: #888; font-size: 13px; text-align: center;">
                — The CollabEditor Team
            </p>
        </div>
        """.formatted(resetLink);


        // Send email via SMTP
        emailService.sendEmail(email, "Password Reset Request", html);

        return ResponseEntity.ok(Map.of("message", "Password reset link sent to your email"));
    }


    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Invalid or expired token"));
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        UserDto user = userService.getUserById(userId);

        //Check if new password is same as old
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "New password cannot be same as your old password"));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(userId,user);

        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }

}
