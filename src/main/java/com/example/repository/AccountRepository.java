package com.example.repository;

import com.example.entity.Account;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository {

    @Transactional
    public Account createAccount(Account account);

    @Transactional
    public List<Account> getAllAccounts();

    @Transactional
    public Account getAccountByUsername(String username);

}
