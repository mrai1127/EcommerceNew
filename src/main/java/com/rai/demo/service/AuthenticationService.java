package com.rai.demo.service;

import com.rai.demo.exceptions.AuthenticationFailException;
import com.rai.demo.model.AuthenticationToken;
import com.rai.demo.model.User;
import com.rai.demo.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;


    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if(Objects.isNull(authenticationToken)){
            return null;
        }
        //authentication token is not null
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthenticationFailException{
        //null check

        if(Objects.isNull(token)){
            throw new AuthenticationFailException("Token does not exist.");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("Token is not valid");
        }
    }
}
