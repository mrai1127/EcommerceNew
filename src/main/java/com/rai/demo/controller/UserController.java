package com.rai.demo.controller;

import com.rai.demo.dto.ResponseDto;
import com.rai.demo.dto.user.SigninDto;
import com.rai.demo.dto.user.SigninResponseDto;
import com.rai.demo.dto.user.SignupDto;
import com.rai.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    //two apis

    @Autowired
    UserService userService;

    //sign up
    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }
    //sign in
    @PostMapping("/signin")
    public SigninResponseDto signIn(@RequestBody SigninDto signinDto){
        return userService.signIn(signinDto);
    }
}
