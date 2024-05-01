package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    private final MessageService messageService;
    private final AccountService accountService;
    
    @Autowired
    SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        if (account.getUsername().isBlank() || account.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be empty");
        } else if (account.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be greater than 4");
        } else if (accountService.getAccountByUsername(account.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Username");
        }
        return new ResponseEntity<Account>(accountService.createAccount(account.getUsername(), account.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        if (accountService.login(account.getUsername(), account.getPassword())) {
            Optional<Account> user = accountService.getAccountByUsername(account.getUsername());
            return ResponseEntity.ok(user.get());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> findAllMessages() {
        return new ResponseEntity<List<Message>>(messageService.getAllMessages(), HttpStatus.OK);
    }
    
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> findMessageById(@PathVariable int messageId) {
        Optional<Message> messageOptional = messageService.getMessageById(messageId);
        if (messageOptional.isPresent()) {
            return new ResponseEntity<>(messageOptional.get(), HttpStatus.OK);
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> findMessagesByUserId(@PathVariable int accountId) {
        return new ResponseEntity<List<Message>>(messageService.getAllMessagesByUserId(accountId), HttpStatus.OK);
    }
    
    @PostMapping("/messages")
    public ResponseEntity<?> addMessage(@RequestBody Message message) {
        Optional<Account> accountOptional = accountService.getAccountById(message.getPostedBy());
        if (!accountOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        } else if (message.getMessageText().isBlank() || message.getMessageText().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be blank");
        } else if (message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be more than 255 characters long");
        }
        Message messageToAdd = messageService.createMessage(message);
        return ResponseEntity.status(200).body(messageToAdd);
    }

    // PATCH            200/400                             "messages/{message_id}"
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> patchMessageText(@PathVariable int messageId, @RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().isEmpty() || message.getMessageText().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty");
        } else if (message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be more than 255 characters long");
        }
        Optional<Message> messageOptional = messageService.getMessageById(messageId);
        if (!messageOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message does not exist");
        }
        return ResponseEntity.ok(1);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteById(@PathVariable int messageId) {
        Optional<Message> toDelete = messageService.getMessageById(messageId);
        if (toDelete.isPresent()) {
            messageService.deleteMessageById(messageId);
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.ok("");
    }

}
