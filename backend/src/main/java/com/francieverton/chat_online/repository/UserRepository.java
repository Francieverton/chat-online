package com.francieverton.chat_online.repository;

import com.francieverton.chat_online.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <UserEntity, Integer> {

    UserEntity findByuserName (String userName);
}
