package com.ishant.TodoList.controller;


import com.ishant.TodoList.DTOs.TodoRequest;
import com.ishant.TodoList.entity.Todos;

import com.ishant.TodoList.service.TodosService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodosService todosService;

    @GetMapping("/my")
    public ResponseEntity<List<Todos>> getTodosForUser(Authentication authentication){
        String username = authentication.getName();
        List<Todos> todos = todosService.getTodosForUser(username);
        return ResponseEntity.ok(todos);
    }
    @PostMapping
    public ResponseEntity<Todos> createTodoForUser(
            @RequestBody TodoRequest todoRequest,
            Authentication authentication) {
        String username = authentication.getName();

        Todos createdTodo = todosService.createTodoForUser(
                username,
                todoRequest.getTitle(),
                todoRequest.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }




    @PutMapping("/{todoId}")
    public ResponseEntity<Todos> updateTodoForUser(
           Authentication authentication,@PathVariable ObjectId todoId,
             @RequestBody TodoRequest todoRequest) {
        String username = authentication.getName();
        Todos updatedTodo = todosService.updateTodoForUser(username,todoId, todoRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);

    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodoOfUser(
            Authentication authentication,@PathVariable ObjectId todoId) {
        String username = authentication.getName();
         todosService.deleteTodoForUser(username,todoId);
         return ResponseEntity.noContent().build();

    }

    @PutMapping("/{todoId}/comp")
    public ResponseEntity<?> todoCompleted(Authentication authentication,@PathVariable ObjectId todoId,@RequestBody TodoRequest todoRequest){
        String username = authentication.getName();
        boolean todo = todosService.completedTodo(username,todoId,todoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(todo) ;
    }



}
