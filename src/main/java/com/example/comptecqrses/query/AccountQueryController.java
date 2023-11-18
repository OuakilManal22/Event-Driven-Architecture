package com.example.comptecqrses.query;

import com.example.comptecqrses.commonApi.commands.queries.GetAllAccountQuery;
import com.example.comptecqrses.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class AccountQueryController
{
    private QueryGateway queryGateway;
    @GetMapping("/allAccounts")
    public List<Account> accountList()
    {
        List<Account> response = queryGateway.query(new GetAllAccountQuery(),
                ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }

}
