package com.neobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {}