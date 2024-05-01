package com.rai.demo.exceptions;

public class AuthenticationFailException extends IllegalArgumentException{
    public AuthenticationFailException (String mgs){
        super(mgs);
    }
}
