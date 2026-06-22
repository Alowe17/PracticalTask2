package com.example.demo.mapper.account;

import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.account.CreateAccountRq;
import com.example.demo.model.entity.account.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(CreateAccountRq createAccountRq);
    AccountDto toDto(Account account);
}