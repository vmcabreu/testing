package com.crai.fluxtest.service;

import com.crai.fluxtest.domain.User;
import com.crai.fluxtest.dto.UserDto;
import com.crai.fluxtest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelmapper;


    public Mono<UserDto> save(UserDto userDto){
        User user = modelmapper.map(userDto, User.class);
        return userRepository.save(user).map(x -> modelmapper.map(x,UserDto.class));
    }

    public Mono<UserDto> update(UserDto userDto, Integer id ){
        User user = modelmapper.map(userDto, User.class);
        user.setId(id);
        return  userRepository.save(user).map(x -> modelmapper.map(x,UserDto.class));
    }

    public Mono<UserDto> getById(Integer id){
        return  userRepository.findById(id).map(x -> modelmapper.map(x,UserDto.class));
    }
}
