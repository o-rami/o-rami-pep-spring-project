package com.example.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.example.entity.Message;
import com.example.repository.mappers.MessageMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.sql.*;

@Repository
public class MessageJdbcTemplateRepository implements MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessageJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Message createMessage(Message message) {
        final String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPostedBy());
            ps.setString(2, message.getMessageText());
            ps.setLong(3, message.getTimePostedEpoch());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        message.setMessageId(keyHolder.getKey().intValue());
        return message;
    }

    @Override
    @Transactional
    public List<Message> getAllMessages() {
        final String sql = "select * from message;";
        return jdbcTemplate.query(sql, new MessageMapper());
    }
    
    @Override
    @Transactional
    public List<Message> getAllMessagesByUserId(int accountId) {
        final String sql = "SELECT * FROM message WHERE posted_by = ?;";
        return jdbcTemplate.query(sql, new MessageMapper(), accountId);
    }

    @Override
    @Transactional
    public Message getMessageById(int messageId) {
        final String sql = "select * from message where message_id = ?;";
        return jdbcTemplate.query(sql, new MessageMapper(), messageId)
            .stream()
            .findFirst()
            .orElse(null);
    }

    @Override
    @Transactional
    public boolean updateMessage(int messageId, Message message) {
        final String sql = "UPDATE message SET "
            + "posted_by = ?, "
            + "message_text = ?, "
            + "time_posted_epoch = ? "
            + "WHERE message_id = ?;";
        return jdbcTemplate.update(sql, message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch(), messageId) > 0;
    }

    @Override
    @Transactional
    public boolean deleteMessageById(int messageId) {
        final String sql = "DELETE FROM message WHERE message_id = ?;";
        return jdbcTemplate.update(sql, messageId) > 0;
    }    
}