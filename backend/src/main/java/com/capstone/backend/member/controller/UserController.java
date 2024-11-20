package com.capstone.backend.member.controller;


import com.capstone.backend.member.dto.UserDTO;
import com.capstone.backend.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDto) {
        System.out.println("Request received at Controller");
        try {
            userService.registerUser(userDto.getEmail(), userDto.getPassword());
            return ResponseEntity.ok("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
        boolean authenticated = userService.authenticateUser(userDto.getEmail(), userDto.getPassword());
        System.out.println("aa");
        if (authenticated) {
            return ResponseEntity.ok("로그인 성공!");
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
        }
    }

}
