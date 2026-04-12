package com.neobank360.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String fullName;
	private String email;
    private String password;
}