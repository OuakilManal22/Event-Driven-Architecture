package com.example.comptecqrses.commonApi.commands.exceptions;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String msg)
    {
        super(msg);
    }
}
