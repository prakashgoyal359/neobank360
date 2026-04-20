package com.neobank360.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    private String receiverAccount;
    private double amount;
}