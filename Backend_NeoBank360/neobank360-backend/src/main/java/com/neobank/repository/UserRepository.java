package com.neobank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.neobank.entity.*;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
