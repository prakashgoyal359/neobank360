package com.neobank360.controller;

import com.neobank360.dto.TransferRequest;
import com.neobank360.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transfer")
    public Map<String, String> transfer(@RequestBody TransferRequest req,
                                        Principal principal) {

        return service.transfer(
                principal.getName(),
                req.getReceiverAccount(),
                req.getAmount()
        );
    }
}