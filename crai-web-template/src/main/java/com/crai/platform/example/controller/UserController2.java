package com.crai.platform.example.controller;


import com.crai.platform.example.dto.UserDto;
import com.crai.platform.example.params.UserParams;
import com.crai.platform.example.service.UserService;
import com.crai.starter.web.dto.RestPageImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("user2")
public class UserController2 {

    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("ioTRestTemplate")
    RestTemplate ioTRestTemplate;

    @Operation(summary = "Save user", description = "Save new user into dataBase", tags = { "example" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public  ResponseEntity<UserDto> save(@RequestBody UserDto userDto){

        return ResponseEntity.ok(userService.save(userDto));
    }
    @Operation(summary = "find User by Id", description = "find the user by %id%", tags = { "example" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<UserDto> getNonReactive(@PathVariable final Long id) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.getById(id));
//        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Find a User by several params", description = "Search user corresponding to %params%", tags = { "example" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping(path = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Page<UserDto>> search(@RequestBody UserParams params){
        return ResponseEntity.ok(userService.search(params));
    }

    @Operation(summary = "Save user", description = "Save new user into dataBase", tags = { "example" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping(path = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public  ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable Long id){

        return ResponseEntity.ok(userService.update(userDto, id));
    }


    @GetMapping("iot-rest")
    @ResponseBody
    public ResponseEntity<RestPageImpl<UserDto>> testCallIoTRestTemplate(){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("x-chorrada","esto_es_chorra");
        HttpEntity<UserParams> request = new HttpEntity<UserParams>(new UserParams(), requestHeaders);
        ResponseEntity<RestPageImpl<UserDto>> response = ioTRestTemplate.exchange("http://localhost:8081/user2/search", HttpMethod.GET,request, new ParameterizedTypeReference<RestPageImpl<UserDto>>(){});
        return response;
    }
}
