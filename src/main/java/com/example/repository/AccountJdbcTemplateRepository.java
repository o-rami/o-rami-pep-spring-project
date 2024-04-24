package com.example.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.mappers.AccountMapper;

import java.util.List;
import java.sql.*;


@Repository
public class AccountJdbcTemplateRepository implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    @Transactional
    public Account createAccount(Account account) {
        final String sql = "INSERT INTO account (username, password) VALUES (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        account.setAccountId(keyHolder.getKey().intValue());
        return account;
    }


    @Override
    @Transactional
    public List<Account> getAllAccounts() {
        final String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, new AccountMapper());
    }

    @Override
    @Transactional
    public Account getAccountByUsername(String username) {
        final String sql = "SELECT * FROM account WHERE username = ?";
        Account result = jdbcTemplate.query(sql, new AccountMapper(), username)
            .stream()
            .findFirst()
            .orElse(null);
        return result;
    }
}
