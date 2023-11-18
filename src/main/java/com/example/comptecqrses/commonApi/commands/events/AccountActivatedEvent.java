package com.example.comptecqrses.commonApi.commands.events;

import com.example.comptecqrses.commonApi.commands.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String>{

    @Getter private AccountStatus accountStatus;
    public AccountActivatedEvent(String id, AccountStatus accountStatus) {
        super(id);

        this.accountStatus = accountStatus;
    }
}
