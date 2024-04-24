package com.example.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import com.example.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {

        Message message = new Message();
        message.setMessageId(rs.getInt("messageId"));
        message.setPostedBy(rs.getInt("postedBy"));
        message.setMessageText(rs.getString("messageText"));
        message.setTimePostedEpoch(rs.getLong("timePostedEpoch"));

        return message;
    }

}