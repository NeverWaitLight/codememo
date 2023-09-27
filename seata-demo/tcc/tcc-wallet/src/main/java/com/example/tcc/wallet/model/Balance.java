package com.example.tcc.wallet.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "t_balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private BigDecimal balance;
    private BigDecimal freeze;
}
