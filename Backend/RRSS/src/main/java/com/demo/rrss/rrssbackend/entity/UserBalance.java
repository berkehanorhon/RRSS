package com.demo.rrss.rrssbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "UserBalance")
public class UserBalance {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

}
