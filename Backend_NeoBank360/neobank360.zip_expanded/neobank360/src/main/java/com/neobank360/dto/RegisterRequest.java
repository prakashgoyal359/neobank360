package com.neobank360.dto;
import lombok.Data;

@Data
public class RegisterRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String email;
    private String address;
    private String accountType;

    private String mobileNumber;
    private String panNumber;
    private String aadharNumber;

}