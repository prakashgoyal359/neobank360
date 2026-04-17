package com.neobank360.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;

    @Column(unique = true)
    private String email;

    private String address;
    private String accountType;

    @Column(unique = true)
    private String mobileNumber;

    @Column(unique = true)
    private String panNumber;

    @Column(unique = true)
    private String aadharNumber;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String accountNumber;

    private String role;
}
