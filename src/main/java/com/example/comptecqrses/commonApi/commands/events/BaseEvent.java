package com.example.comptecqrses.commonApi.commands.events;

import lombok.Getter;

public abstract class BaseEvent<T>
{
   @Getter  private T id;

    protected BaseEvent(T id) {
        this.id = id;
    }
}
