package com.example.demo.service.account;

import com.example.demo.exception.custom.AccountInvalidDataException;
import com.example.demo.exception.custom.AccountNotFoundException;
import com.example.demo.mapper.account.AccountMapper;
import com.example.demo.model.dto.account.AccountDto;
import com.example.demo.model.dto.account.CreateAccountRq;
import com.example.demo.model.dto.account.UpdateAccountRq;
import com.example.demo.model.entity.account.Account;
import com.example.demo.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public void create (CreateAccountRq accountRq) {
        accountRepository.save(accountMapper.toEntity(accountRq));
    }

    @Transactional
    public void deleteById (Long id) {
        accountRepository.deleteById(id);
    }

    @Transactional
    public void update (AccountDto dto, UpdateAccountRq accountRq) {
        if (isUpdateDataEmpty(accountRq)) {
            throw new AccountInvalidDataException("Нет данных для обновления аккаунта!");
        }

        Account account = getAccountById(dto.getId());

        if (accountRq.getName() != null) {
            account.setName(accountRq.getName());
        }

        if (accountRq.getSurname() != null) {
            account.setSurname(accountRq.getSurname());
        }

        if (accountRq.getAge() != null) {
            account.setAge(accountRq.getAge());
        }
    }

    public AccountDto getAccount (Account account) {
        return accountMapper.toDto(account);
    }

    private boolean isUpdateDataEmpty (UpdateAccountRq accountRq) {
        return (accountRq.getName() == null || accountRq.getName().isBlank())
                && (accountRq.getSurname() == null || accountRq.getSurname().isBlank())
                && accountRq.getAge() == null;
    }

    public Account getAccountById (Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Не удалось найти аккаунт по указанному номеру!"));
    }
}