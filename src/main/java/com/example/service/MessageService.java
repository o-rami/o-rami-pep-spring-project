package com.example.service;

import com.example.repository.MessageRepository;

import javassist.NotFoundException;

import com.example.entity.Message;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (!validateMessageText(message.getMessageText())) {
            return null;
        }
        if (messageRepository.existsById(message.getMessageId())) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public List<Message> getAllMessagesByUserId(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }

    public Message getMessageById(int messageId) throws NotFoundException {
        return messageRepository.findById(messageId)
            .orElseThrow(() -> new NotFoundException("Message#" + messageId + " was not found."));
    }

    public void updateMessage(Message message) {
        if (messageRepository.existsById(message.getMessageId())) {
            messageRepository.save(message);
        }
    }

    public Message patchMessage(Message message, String messageText) {
        if (!validateMessageText(messageText)) {
            return message;
        }
        if (messageRepository.existsById(message.getMessageId())) {
            message.setMessageText(messageText);
        }
        return messageRepository.save(message);
    }

    public void deleteMessageById(int messageId) {
        messageRepository.deleteById(messageId);
    }

    private boolean validateMessageText(String text) {
        return !text.isBlank() 
        || !text.isEmpty()
        || text.length() <= 255;
    }
}
