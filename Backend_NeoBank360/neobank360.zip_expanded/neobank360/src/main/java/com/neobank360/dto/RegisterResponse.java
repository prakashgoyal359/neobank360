package com.neobank360.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
	private Long id;
    private String email;
    private String fullName;
    private String message;
}
