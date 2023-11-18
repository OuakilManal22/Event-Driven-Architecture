package com.example.comptecqrses.commands.controllers;

import com.example.comptecqrses.commonApi.commands.CreateAccountCommand;
import com.example.comptecqrses.commonApi.commands.CreditAccountCommand;
import com.example.comptecqrses.commonApi.commands.DebitAccountCommand;
import com.example.comptecqrses.commonApi.commands.dtos.CreateAccountDTO;
import com.example.comptecqrses.commonApi.commands.dtos.CreditAccountDTO;
import com.example.comptecqrses.commonApi.commands.dtos.DebitAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController
{
    private CommandGateway commandGateway;
    public EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO request)
    {
        CompletableFuture<String> commandResponse =  commandGateway.send(new CreateAccountCommand
                (
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request)
    {
        CompletableFuture<String> commandResponse =  commandGateway.send(new CreditAccountCommand
                (
                        request.getAccountId(),
                        request.getAmount(),
                        request.getCurrency()
                ));
        return commandResponse;
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountDTO request)
    {
        CompletableFuture<String> commandResponse =  commandGateway.send(new DebitAccountCommand(

                        request.getAccountId(),
                        request.getAmount(),
                        request.getCurrency()
                ));
        return commandResponse;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>  exceptionHandler(Exception exception){
        ResponseEntity<String>  entity = new ResponseEntity<>(exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);

                return entity;

    }
    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId)
    {
        return eventStore.readEvents(accountId).asStream();
    }
}
