package com.rrss2024.RRSS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rrss2024.RRSS.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
