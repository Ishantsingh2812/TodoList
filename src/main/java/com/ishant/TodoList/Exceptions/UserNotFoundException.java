package com.ishant.TodoList.Exceptions;


public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
