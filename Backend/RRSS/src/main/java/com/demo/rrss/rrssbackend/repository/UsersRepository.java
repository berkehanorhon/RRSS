package com.demo.rrss.rrssbackend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
	Optional<Users> findByUsername(String username);
}