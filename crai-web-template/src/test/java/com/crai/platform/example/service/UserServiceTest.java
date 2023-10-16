package com.crai.platform.example.service;

import com.crai.platform.example.domain.User;
import com.crai.platform.example.dto.UserDto;
import com.crai.platform.example.repository.UserRepository;
import com.crai.starter.jpa.exception.DataException;
import com.crai.starter.jpa.exception.NoDataFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    ModelMapper modelMapper=new ModelMapper();
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }



    @Test
    void save() {

        when(userRepository.save(any())).thenReturn(User.builder().id(1).userName("Juan").build());
        UserDto dto = userService.save(UserDto.builder().userName("Juan").build());
        assertEquals("Juan",dto.getUserName());
    }

    @Test
    void update() {
        //when(userRepository.save(any())).thenReturn(User.builder().id(1).userName("Juan").build());
        when(userRepository.findById(any())).thenReturn(Optional.of(User.builder().id(1).userName("Juan").build()));
        when(userRepository.save(any())).thenReturn(User.builder().id(1).userName("Juan2").lastName("Perez").build());
        UserDto dto = userService.update(UserDto.builder().userName("Juan2").build(), Long.valueOf("1"));
        assertEquals("Juan2",dto.getUserName());
    }

    @Test
    void getByIdException() {
        assertThrows(NoDataFoundException.class,()-> userService.getById(Long.valueOf("1")));


    }
    @Test
    void getById() {
        when(userRepository.findById(Long.valueOf("1"))).thenReturn(Optional.of(User.builder().id(1).userName("Juan").build()));
        UserDto dto = userService.getById(Long.valueOf("1"));
        assertEquals("Juan",dto.getUserName());



    }
}