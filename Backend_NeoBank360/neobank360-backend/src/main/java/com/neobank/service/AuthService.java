package com.neobank.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.security.crypto.password.*;
import com.neobank.repository.*;
import com.neobank.entity.*;

@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;

    public User register(User u){
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public User login(String email, String password){

        User user = repo.findByEmail(email);

        if(user.isAccountLocked()) throw new RuntimeException("Locked");

        if(!encoder.matches(password,user.getPassword())){
            user.setFailedAttempts(user.getFailedAttempts()+1);
            if(user.getFailedAttempts()>=3) user.setAccountLocked(true);
            repo.save(user);
            throw new RuntimeException("Invalid");
        }

        user.setFailedAttempts(0);
        return repo.save(user);
    }
}