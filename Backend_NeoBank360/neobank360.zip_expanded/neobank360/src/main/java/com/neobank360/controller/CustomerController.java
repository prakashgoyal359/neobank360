package com.neobank360.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @GetMapping("/dashboard")
    public String customer() {
        return "Welcome Customer";
    }
}