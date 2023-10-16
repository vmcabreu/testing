package com.crai.platform.agenda.controller;

import com.crai.platform.agenda.dto.AgendaDto;
import com.crai.platform.agenda.params.AgendaParams;
import com.crai.platform.agenda.service.AgendaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaControllerTest {
    @Autowired
    private MockMvc mvc;
    @Mock
    private AgendaService agendaService;

    @InjectMocks
    private AgendaController agendaController;


    @Test
    void search_ReturnsOkResponse_WhenServiceReturnsPage() throws ExecutionException, InterruptedException {
        // Arrange
        AgendaParams params = new AgendaParams();
        Page<AgendaDto> expectedPage = new PageImpl<>(List.of(new AgendaDto("CUERPODESEGURIDAD","Canarias",6666666,"Example@mail.com")));

        when(agendaService.search(params)).thenReturn(expectedPage);

        ResponseEntity<Page<AgendaDto>> response = agendaController.search(params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPage, response.getBody());
        verify(agendaService, times(1)).search(params);
    }
    @Test
    void testSearchBadRequest() throws ExecutionException, InterruptedException {
        // Arrange
        AgendaParams params = new AgendaParams();
        when(agendaService.search(params)).thenThrow(IllegalArgumentException.class);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            agendaController.search(params);
        });
    }

}
