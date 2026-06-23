package com.example.demo.controller.api;

import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.account.CreateAccountRq;
import com.example.demo.model.dto.account.UpdateAccountRq;
import com.example.demo.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<AccountDto> getAccount (AccountDto accountDto) {
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAccount (@RequestBody UpdateAccountRq updateAccountRq, AccountDto accountDto) {
        accountService.update(accountDto, updateAccountRq)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAccount (@Valid @RequestBody CreateAccountRq createAccountRq) {
        accountService.create(createAccountRq);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount (AccountDto accountDto) {
        accountService.delete(accountService.getAccountById(accountDto.getUuid()));
        return ResponseEntity.ok().build();
    }
}