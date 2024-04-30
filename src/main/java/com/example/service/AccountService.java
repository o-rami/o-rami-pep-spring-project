package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {

    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        Optional<Account> accountOptional = accountRepository.findByUsername(account.getUsername());
        if (accountOptional.isPresent()) {
            return null;
        }
        if (account.getUsername().isBlank()
                || account.getUsername().isEmpty()
                || account.getPassword().length() < 4) {
            return null;
        }
        return accountRepository.save(account);
    }

    public void login(String username, String password) {
        
    }
    
    public Optional<Account> getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
