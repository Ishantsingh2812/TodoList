package com.ishant.TodoList.Exceptions;

public class UnauthorizedTodoAccessException extends RuntimeException{
    public UnauthorizedTodoAccessException(String message){
        super(message);
    }
}
