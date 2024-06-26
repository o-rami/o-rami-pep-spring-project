package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;
import java.util.Optional;
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
        if (validateMessageText(message.getMessageText())) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByUserId(int postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public Message updateMessage(Message message) {
        Optional<Message> messageOptional = messageRepository.findById(message.getMessageId());
        if (messageOptional.isPresent()) {
            return messageRepository.save(message);
        }
        return null;
    }

    public Optional<Message> patchMessage(int messageId, String messageText) {
        if (!validateMessageText(messageText)) {
            return null;
        }
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            messageOptional.get().setMessageText(messageText);
            messageRepository.save(messageOptional.get());
        }
        return messageOptional;
    }

    public void deleteMessageById(int messageId) {
        messageRepository.deleteById(messageId);
    }

    private boolean validateMessageText(String text) {
        return text.isBlank() 
            || text.isEmpty()
            || text.length() > 255;
    }
}
