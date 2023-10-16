package com.crai.platform.example.service;


import com.crai.platform.example.domain.User;
import com.crai.platform.example.dto.UserDto;
import com.crai.platform.example.params.UserParams;
import com.crai.platform.example.repository.UserRepository;
import com.crai.platform.example.specifications.UserSpecification;
import com.crai.platform.thingworxclient.api.ApiClient;
import com.crai.platform.thingworxclient.api.v1.UserInfoApi;
import com.crai.platform.thingworxclient.dto.GetUserInfoPost200ResponseDTO;
import com.crai.platform.thingworxclient.dto.GetUserInfoPostRequestDTO;
import com.crai.starter.jpa.exception.DataException;
import com.crai.starter.jpa.exception.NoDataFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelmapper;

    @Autowired
    UserInfoApi userInfoApi;

    public GetUserInfoPost200ResponseDTO callIotGetClient(String userId){
        GetUserInfoPostRequestDTO requestDTO = new GetUserInfoPostRequestDTO();
        requestDTO.setUserId(userId);
        GetUserInfoPost200ResponseDTO dto = userInfoApi.getUserInfoPost(requestDTO);
        return dto;
    }

    public UserDto save(UserDto userDto) {
        User user = modelmapper.map(userDto, User.class);
        return modelmapper.map(userRepository.save(user), UserDto.class);
    }

    public UserDto update(UserDto userDto, Long id) {
        Optional<User> userOp = userRepository.findById(id);
        if(userOp.isPresent()){
            User user = userOp.get();
            modelmapper.map(userDto, user);
            user.setId(id);
            return modelmapper.map(userRepository.save(user), UserDto.class);
        } else {
            throw  new NoDataFoundException();
        }


    }

    public UserDto getById(Long id) {

        return modelmapper.map(
                userRepository.findById(id).
                        orElseThrow(() -> new NoDataFoundException()), UserDto.class);
    }

    public List<UserDto> findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .stream()
                .map(u -> modelmapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> findByUserNameAge(String userName, Integer age){
        return userRepository.findByUserNameAge(userName,age)
                .stream()
                .map(u -> modelmapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public Page<UserDto> search(UserParams params){
        UserParams userParam = params == null ? new UserParams(): params;
        UserSpecification userSpecification = new UserSpecification(userParam);
        return userRepository.findAll(userSpecification, userSpecification.getUpdatable())
                .map(u -> modelmapper.map(u,UserDto.class));
    }
}
