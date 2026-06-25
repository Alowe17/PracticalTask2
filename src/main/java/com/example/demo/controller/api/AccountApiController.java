package com.example.demo.controller.api;

import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.account.CreateAccountRq;
import com.example.demo.model.dto.account.UpdateAccountRq;
import com.example.demo.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;

    @GetMapping
    public AccountDto getAccount (AccountDto accountDto) {
        return accountDto;
    }

    @PutMapping
    public void updateAccount (@RequestBody UpdateAccountRq updateAccountRq, AccountDto accountDto) {
        accountService.update(accountDto, updateAccountRq);
    }

    @PostMapping
    public void createAccount (@Valid @RequestBody CreateAccountRq createAccountRq) {
        accountService.create(createAccountRq);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount (@PathVariable Long id) {
        accountService.deleteById(id);
    }
}