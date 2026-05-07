package com.neobank.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Data
public class Transaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;
    private String type;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
}