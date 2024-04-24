package com.example.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import com.example.entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        Account account = new Account();
        account.setAccountId(rs.getInt("accountId"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));

        return account;
    }

}
