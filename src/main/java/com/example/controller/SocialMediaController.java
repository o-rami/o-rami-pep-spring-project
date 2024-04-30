package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import com.example.service.MessageService;
import com.example.entity.Message;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    
    private final MessageService messageService;
    
    @Autowired
    SocialMediaController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    // POST             200/400                             "/register"

    // POST             200/401                             "/login"

    
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
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message messageToAdd = messageService.createMessage(message);
        if (messageToAdd != null) {
            return new ResponseEntity<>(messageToAdd, HttpStatus.OK);
        }
        return new ResponseEntity<>(messageToAdd, HttpStatus.BAD_REQUEST);
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

    // DELETE           200                                 "/messages/{message_id}"
    // @DeleteMapping("/messages/{messageId}")
    // public @ResponseBody ResponseEntity<?> deleteById(@PathVariable int messageId) {
    //     Message toDelete = messageService.getMessageById(messageId);
    //     if (toDelete != null) {
    //         messageService.deleteMessageById(messageId);
    //         return ResponseEntity.ok(1);
    //     }
    //     return ResponseEntity.ok();
    // }
    

    // GET              200                                 "/accounts/{account_id}/messages"
    // @GetMapping("/accounts/{accountId}/message")
    // public @ResponseBody

}
