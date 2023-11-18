package com.example.comptecqrses.services;

import com.example.comptecqrses.commonApi.commands.enums.OperationType;
import com.example.comptecqrses.commonApi.commands.events.AccountActivatedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountCreatedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountCreditedEvent;
import com.example.comptecqrses.commonApi.commands.events.AccountDebitedEvent;
import com.example.comptecqrses.commonApi.commands.queries.GetAllAccountQuery;
import com.example.comptecqrses.entities.Account;
import com.example.comptecqrses.entities.Operation;
import com.example.comptecqrses.repositories.AccountRepository;
import com.example.comptecqrses.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler
{
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    public void on(AccountCreatedEvent event)
    {   log.info("**************");
        log.info("AccountCreatedEvent received");
        Account account = new Account();

                account.setId(event.getId());
                account.setCurrency(event.getCurrency());
                account.setStatus(event.getStatus());
                account.setBalance(event.getInitialBalance());
                account.setOperations(null);
                accountRepository.save(account);
    }
    public void on(AccountActivatedEvent event)
    {   log.info("**************");
        log.info("AccountActivatedEvent received");
      Account account = accountRepository.findById(event.getId()).get();
      account.setStatus(event.getAccountStatus());
      accountRepository.save(account);

    }
    public void on(AccountDebitedEvent event)
    {   log.info("**************");
        log.info("AccountDebitedvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()-event.getAmount());
        accountRepository.save(account);

    }

    public void on(AccountCreditedEvent event)
    {   log.info("**************");
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).get();
        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);
        account.setBalance(account.getBalance()+event.getAmount());
        accountRepository.save(account);

    }
    @QueryHandler
    public List<Account> on(GetAllAccountQuery query)
    {
        return accountRepository.findAll();
    }

}
