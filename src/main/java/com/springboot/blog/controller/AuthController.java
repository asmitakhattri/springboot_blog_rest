package com.springboot.blog.controller;

import com.springboot.blog.playload.JWTAuthResponse;
import com.springboot.blog.playload.LoginDto;
import com.springboot.blog.playload.RegisterDto;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //build login rest api
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }
    // build register rest api
    @PostMapping(value ={"/register"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
       String response = authService.register(registerDto);
       return new ResponseEntity<>(response, HttpStatus.CREATED);



    }
}