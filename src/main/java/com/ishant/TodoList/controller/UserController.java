package com.ishant.TodoList.controller;

import com.ishant.TodoList.DTOs.CreateUserRequest;
import com.ishant.TodoList.entity.User;
import com.ishant.TodoList.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserRequest createUserRequest) {
       userService.registerUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateUserRequest request){
        String result = userService.loginUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable ObjectId id) {
        return userService.findById(id);
    }


    @GetMapping("/exists")
    public boolean usernameExists(@RequestParam String username){
        return userService.existsByUsername(username);
    }

}
