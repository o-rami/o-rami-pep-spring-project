package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

@Controller
public class SocialMediaController {
    
    private final MessageService messageService;
    
    @Autowired
    SocialMediaController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    // POST             200/400                             "/register"
    
    // POST             200/401                             "/login"
    

    // GET                                                  "/messages"
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> findAllMessages() {
        return new ResponseEntity<List<Message>>(messageService.getAllMessages(), HttpStatus.OK);
    }
    

    // GET              200                                 "/messages/{message_id}"
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> findMessageById(@PathVariable int messageId) {
        return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
    }
    

    // POST             200/400                             "/messages"


    // PATCH            200/400                             "messages/{message_id}"


    // DELETE           200                                 "/messages/{message_id}"
    

    // GET              200                                 "/accounts/{account_id}/messages"
    
}
