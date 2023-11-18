package com.example.comptecqrses.commonApi.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class DebitAccountDTO
{
    private String accountId;
    private double amount;
    private String currency;

}
