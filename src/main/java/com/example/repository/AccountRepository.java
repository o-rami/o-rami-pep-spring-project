package com.example.repository;

import com.example.entity.Account;
import java.util.List;

public interface AccountRepository {

    public Account createAccount(Account account);

    public List<Account> getAllAccounts();
    
}
