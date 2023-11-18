package com.example.comptecqrses.commonApi.commands.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountDTO
{
    private double initialBalance;
    private String currency;

}
