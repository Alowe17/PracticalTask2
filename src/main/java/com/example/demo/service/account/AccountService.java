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
        log.info("Аккаунт успешно создан!");
    }

    @Transactional
    public void delete (Account account) {
        accountRepository.delete(account);
        log.info("Аккаунт с id {} успешно удален!", account.getId());
    }

    @Transactional
    public void update (Account account, UpdateAccountRq accountRq) {
        if (isUpdateDataEmpty(accountRq)) {
            log.warn("Нет данных для обновления аккаунта с id {}!", account.getId());
            throw new AccountInvalidDataException("Нет данных для обновления аккаунта!");
        }

        if (accountRq.getName() != null && !accountRq.getName().isBlank() && accountRq.getName().length() < 100) {
            account.setName(accountRq.getName());
        }

        if (accountRq.getSurname() != null && !accountRq.getSurname().isBlank() && accountRq.getSurname().length() < 100) {
            account.setSurname(accountRq.getSurname());
        }

        if (accountRq.getAge() != null && accountRq.getAge() > 0) {
            account.setAge(accountRq.getAge());
        }

        log.info("Пользователь с id {} успешно обновлен!", account.getId());
    }

    public AccountDto getAccount (Account account) {
        return accountMapper.toDto(account);
    }

    private boolean isUpdateDataEmpty (UpdateAccountRq accountRq) {
        return accountRq.getName() == null && accountRq.getSurname() == null && accountRq.getAge() == null;
    }

    public Account getAccountById (Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Аккаунт с id {} не найден!", id);
                    return new AccountNotFoundException("Не удалось найти аккаунт по указанному номеру!");
                });
    }
}