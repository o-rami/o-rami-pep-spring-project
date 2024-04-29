package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> findAllMessages() {
        return messageService.getAllMessages();
    }

    // @GetMapping("/messages/{messageId}")
    // @ResponseStatus()
    // public ResponseEntity<Message> findMessageById(@PathVariable int messageId) {
    //     return messageService.getMessageById(messageId);
    // }

    // @PostMapping("/messages")
    // @ResponseStatus(HttpStatus.CREATED)
    // public Message addMessage(@RequestBody Message message) {
        
    // }


    // @ExceptionHandler({RuntimeException.class, CustomException.class})
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public String handleNotFound(RuntimeException ex) {
    //     return ex.getMessage();
    // }

    // @ExceptionHandler(MissingServletRequestParameterException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // public String handleMissingParams(MissingServletRequestParameterException ex) {
    //     return ex.getParameterName() + " is missing in the query parameters and is required.";
    // }
}
