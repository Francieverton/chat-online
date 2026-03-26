package com.francieverton.chat_online.controller;

import com.francieverton.chat_online.entity.UserEntity;
import com.francieverton.chat_online.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public List<UserEntity> ListAll() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public UserEntity createdUser (@RequestBody UserEntity user){
        String cryptPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(cryptPass);
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public UserEntity login (@RequestBody UserEntity user){
        UserEntity userBank = userRepository.findByUserName(user.getUserName());

        if (userBank == null){
            throw new RuntimeException("Usuário não encontrado");
        }
        boolean equalPassword = bCryptPasswordEncoder.matches(user.getPassword(), userBank.getPassword());

        if (!equalPassword){
            throw new RuntimeException("Senha não compatível");
        }

        return userBank;
    }
}
