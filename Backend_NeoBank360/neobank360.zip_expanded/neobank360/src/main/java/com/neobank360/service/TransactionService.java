package com.neobank360.service;

import com.neobank360.entity.*;
import com.neobank360.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserRepository userRepo;
    private final TransactionRepository txnRepo;

    public Map<String, String> transfer(String senderUsername, String receiverAcc, double amount) {

        User sender = userRepo.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepo.findByAccountNumber(receiverAcc)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance ❌");
        }

        // 💸 Transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepo.save(sender);
        userRepo.save(receiver);

        // 📊 Save Transaction
        Transaction txn = Transaction.builder()
                .sender(sender.getUsername())
                .receiver(receiver.getUsername())
                .amount(amount)
                .build();

        txnRepo.save(txn);

        return Map.of("message", "Transfer Successful ✅");
    }
}