package com.ishant.TodoList.DTOs;


import lombok.Data;

@Data
public class TodoRequest {
    private String title;
    private String description;
    private boolean completed;

}

