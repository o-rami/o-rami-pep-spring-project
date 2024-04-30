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

    // POST             200/401                             "/login"
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
    public ResponseEntity<Message> findMessageById(@PathVariable int messageId) {
        return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
    }
    

    // POST             200/400                             "/messages"
    @PostMapping("/messages")
    public ResponseEntity<?> addMessage(@RequestBody Message message) {
        Message messageToAdd = messageService.createMessage(message);
        Message creator = messageService.getMessageById(message.getPostedBy());
        if (messageToAdd != null && creator != null) {
            return new ResponseEntity<>(messageToAdd, HttpStatus.OK);
        }
        return ResponseEntity.status(400).body("");
    }

    // PATCH            200/400                             "messages/{message_id}"
    // @PatchMapping("/messages/{messageId}")
    // public ReponseEntity<?> patchMessageText(@PathVariable int messageId, @RequestBody String messageText) {
    //     Message message = messageService.patchMessage(messageId, messageText);
    //     if (message != null) {
    //         return new ResponseEntity<Message>(message, HttpStatus.OK);
    //     }
    //     return ResponseEntity.badRequest();
    // }

    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<?> deleteById(@PathVariable int messageId) {
        Message toDelete = messageService.getMessageById(messageId);
        if (toDelete != null) {
            messageService.deleteMessageById(messageId);
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.ok("");
    }
    

    // GET              200                                 "/accounts/{account_id}/messages"
    // @GetMapping("/accounts/{accountId}/message")
    // public @ResponseBody

}
