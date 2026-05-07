package com.neobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}