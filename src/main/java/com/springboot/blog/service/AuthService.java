package com.springboot.blog.service;

import com.springboot.blog.playload.LoginDto;
import com.springboot.blog.playload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);

}
