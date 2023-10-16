package com.crai.fluxtest.controller;

import com.crai.fluxtest.dto.UserDto;
import com.crai.fluxtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    UserService userService;

    @PostMapping()
    public Mono<UserDto> save(@RequestBody UserDto userDto){
        return userService.save(userDto).log();
    }
    @GetMapping("/{id}")
    public Mono<UserDto> getById(@PathVariable final Integer id){
        return userService.getById(id).log();
    }

    @GetMapping("/non-reactive/{id}")
    public ResponseEntity<UserDto> getNonReactive(@PathVariable final Integer id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.getById(id).toFuture().get());
//        return ResponseEntity.ok(userService.getById(id));
    }
}
