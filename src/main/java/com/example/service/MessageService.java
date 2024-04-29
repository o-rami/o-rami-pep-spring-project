package com.example.service;

import com.example.repository.MessageRepository;

import javassist.NotFoundException;

import com.example.entity.Message;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

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
        if (message.getMessageText().isBlank() 
            || message.getMessageText().isEmpty()
            || message.getMessageText().length() > 255) {
            return null;
        }

        List<Message> messages = (List<Message>) messageRepository.findAll();
        if (messages.contains(message)) {
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public List<Message> getAllMessagesByUserId(int postedBy) {
        List<Optional<Message>> listOfMessages = messageRepository.findAllByPostedBy(postedBy);
        List<Message> result = new ArrayList<>();
        for (Optional<Message> m : listOfMessages) {
            if (m.get() != null) {
                result.add(m.get());
            }
        }
        return result;
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

    public void patchMessage(Message message, String messageText) {
        if (validateMessageText(messageText)) {
            return;
        }
        if (messageRepository.existsById(message.getMessageId())) {
            message.setMessageText(messageText);
            messageRepository.save(message);
        }
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
