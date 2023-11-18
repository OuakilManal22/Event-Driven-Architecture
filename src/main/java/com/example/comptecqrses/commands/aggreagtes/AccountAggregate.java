package com.example.comptecqrses.commands.aggreagtes;

import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonApi.commands.CreditAccountCommand;
import com.example.comptecqrses.commonApi.commands.DebitAccountCommand;
import com.example.comptecqrses.commonApi.commands.enums.AccountStatus;
import com.example.comptecqrses.commonApi.commands.events.AccountActivatedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountCreatedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountCreditedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountDebitedEvent;
import com.example.comptecqrses.commonApi.commands.exceptions.AmountNegativeException;
import com.example.comptecqrses.commonApi.commands.exceptions.BalanceNotSufficientException;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class AccountAggregate
{
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;



    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand)
    {
        if(createAccountCommand.getInitialBalance()<0) throw
        new RuntimeException("Impossible!");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(), createAccountCommand.getInitialBalance(), createAccountCommand.getCurrency(),
                AccountStatus.CREATED));
    }

    //MUTER L ETAT D EVENEMENT
    @EventSourcingHandler
    public void on(AccountCreatedEvent event)
    {
        this.accountId = event.getId();
        this.balance= event.getInitialBalance();
        this.currency=event.getCurrency();
        this.status = AccountStatus.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(),
                AccountStatus.ACTIVATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {

        this.status=event.getAccountStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command)
    {
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                 command.getCurrency()
                )
        );
    }

    @CommandHandler
    public void handle(DebitAccountCommand command)
    {
        if(this.balance<command.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient Exception!");
        if(command.getAmount()<0) throw new AmountNegativeException("Amount should not be negative");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                        command.getId(),
                        command.getAmount(),
                        command.getCurrency()
                )
        );
    }

    //FONCTION D EVOLUTION
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {

        this.balance+=event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {

        this.balance-=event.getAmount();
    }
}
