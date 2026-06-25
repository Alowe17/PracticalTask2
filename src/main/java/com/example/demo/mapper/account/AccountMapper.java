package com.example.demo.mapper.account;

import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.account.CreateAccountRq;
import com.example.demo.model.entity.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(CreateAccountRq createAccountRq);

    @Mapping(source = "account.id", target = "id")
    AccountDto toDto(Account account);
}