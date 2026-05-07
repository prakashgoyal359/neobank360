package com.neobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {}