package com.crai.platform.event.example.controller;

import com.crai.platform.event.example.dto.ParamEvent;
import com.crai.platform.event.example.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("event/sender")
@Slf4j
public class SenderTestController {

    @Autowired
    SenderService sender;

    @PostMapping(value = "/{module}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> sendEvent(@PathVariable(name = "module") String module, @RequestBody ParamEvent event) {
        sender.sendEvent(event);
        return ResponseEntity.ok("ok");
    }


//    @GetMapping(value = "/{module}")
//    @ResponseBody
//    public ResponseEntity<String> sendEventGet(@PathVariable(name = "module") String module, @RequestBody) {
//        sender.sendEvent(module);
//        return ResponseEntity.ok("ok");
//    }



}
