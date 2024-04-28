package com.example.repository;

import com.example.entity.Message;
import java.util.List;

public interface MessageRepository {

    public Message createMessage(Message message);

    public List<Message> getAllMessages();

    public List<Message> getAllMessagesByUserId(int accountId);

    public Message getMessageById(int messageId);

    public boolean updateMessage(int messageId, Message message);

    public boolean deleteMessageById(int messageId);
}
