package com.ishant.TodoList.service;

import com.ishant.TodoList.DTOs.CreateUserRequest;
import com.ishant.TodoList.Exceptions.UserAlreadyExistException;
import com.ishant.TodoList.Exceptions.UserNotFoundException;
import com.ishant.TodoList.entity.User;
import com.ishant.TodoList.repository.UserRepository;
import com.ishant.TodoList.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;





    public User registerUser(CreateUserRequest dto) {

        if (existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistException("Username already taken, please choose another.");
        }


        // Convert DTO → Entity
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    public String loginUser(String username, String password){

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty()){
            return "User not found";
        }

        User user = optionalUser.get();   // extract real user

        if(passwordEncoder.matches(password, user.getPassword())){
            return jwtUtil.generateToken(username);
        }else{
            return "Invalid password";
        }
    }


    public User findById(ObjectId id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}

