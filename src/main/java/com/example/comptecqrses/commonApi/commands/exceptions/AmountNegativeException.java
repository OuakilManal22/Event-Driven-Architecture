package com.example.comptecqrses.commonApi.commands.exceptions;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String msg)
    {
        super(msg);
    }
}
