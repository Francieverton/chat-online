package com.francieverton.chat_online.controller;

import com.francieverton.chat_online.entity.UserEntity;
import com.francieverton.chat_online.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserEntity> ListAll() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public UserEntity createdUser (@RequestBody UserEntity user){
        return userRepository.save(user);
    }
}
