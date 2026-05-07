package com.neobank360.service;

import com.neobank360.entity.User;

import com.neobank360.repository.UserRepository;
import com.neobank360.util.GeneratorUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository repo;
  
    private final PasswordEncoder encoder;

    // ✅ GET ALL USERS
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ✅ DELETE USER
    public String deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found ❌");
        }

        repo.deleteById(id);
        return "User deleted successfully ✅";
    }

    // ✅ UPDATE USER
    public User updateUser(Long id, User updatedUser) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setAccountType(updatedUser.getAccountType());
        user.setBalance(updatedUser.getBalance());

        return repo.save(user);
    }

    // ✅ CREATE USER
    public User createUser(User user) {
        return repo.save(user);
    }
    
    public String approveUser(
            Long id,
            String username,
            String password
    ) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setUsername(username);

        user.setPassword(
                encoder.encode(password)
        );

        user.setStatus("APPROVED");

        user.setAccountNumber(
                GeneratorUtil.generateAccountNumber()
        );

        user.setFirstLogin(true);

        repo.save(user);

        return username;
    }
    public String rejectUser(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus("REJECTED");

        repo.save(user);

        return "Rejected ❌";
    }
    
}