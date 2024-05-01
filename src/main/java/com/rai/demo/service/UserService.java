package com.rai.demo.service;


import com.rai.demo.dto.ResponseDto;
import com.rai.demo.dto.user.SigninDto;
import com.rai.demo.dto.user.SigninResponseDto;
import com.rai.demo.dto.user.SignupDto;
import com.rai.demo.exceptions.AuthenticationFailException;
import com.rai.demo.exceptions.CustomException;
import com.rai.demo.model.AuthenticationToken;
import com.rai.demo.model.User;
import com.rai.demo.repository.TokenRepository;
import com.rai.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {

        //check wheather user is present or not first
        if(Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already present");
        }
        //hash  the password
        String encryptedpassword = signupDto.getPassword();
        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //save the user now
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setPassword(encryptedpassword);
        userRepository.save(user);

        //create the token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SigninResponseDto signIn(SigninDto signinDto) {
        //first we have find user by email
        User user = userRepository.findByEmail(signinDto.getEmail());

        if(Objects.isNull(user)){
            throw new AuthenticationFailException(("User is not valid"));
        }
        //hash the password
        try {
           if(!user.getPassword().equals(hashPassword(signinDto.getPassword()))){
               throw new AuthenticationFailException("wrong password");
           }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //compare the password in db

        //if password is matching than retrieve the token and return response
        AuthenticationToken token = authenticationService.getToken(user);

        if(Objects.isNull(token)){
            throw new CustomException("token is not present");
        }

        return new SigninResponseDto("success", token.getToken());
    }
}
