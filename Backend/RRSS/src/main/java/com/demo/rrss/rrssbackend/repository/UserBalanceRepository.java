package com.demo.rrss.rrssbackend.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.rrss.rrssbackend.entity.UserBalance;

import java.math.BigDecimal;

@Repository
public interface UserBalanceRepository extends CrudRepository<UserBalance, Long> {
    @Query("SELECT u.balance FROM UserBalance u WHERE u.userId = :userId")
    BigDecimal findBalanceByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE UserBalance u SET u.balance = :balance WHERE u.userId = :userId")
    void updateUserBalanceByUserId(@Param("userId") Long userId, @Param("balance") BigDecimal balance);
}
