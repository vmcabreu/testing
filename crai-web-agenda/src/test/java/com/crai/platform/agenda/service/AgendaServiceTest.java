package com.crai.platform.agenda.service;

import com.crai.platform.agenda.domain.Agenda;
import com.crai.platform.agenda.dto.AgendaDto;
import com.crai.platform.agenda.enums.DepartmentsEnum;
import com.crai.platform.agenda.params.AgendaParams;
import com.crai.platform.agenda.repository.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.crai.platform.agenda.specifications.AgendaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Spy
    ModelMapper modelMapper=new ModelMapper();

    @InjectMocks
    private AgendaService agendaService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }
/*
*public Page<AgendaDto> search(AgendaParams params){
       AgendaParams agendaParams = params ==null ? new AgendaParams(): params;
        AgendaSpecification agendaSpecification = new AgendaSpecification(agendaParams);
        return agendaRepository.findAll(agendaSpecification,agendaSpecification.getUpdatable())
                .map(agenda -> modelmapper.map(agenda,AgendaDto.class));

    }
* */
@Test
void search() {
    List<Agenda> agendasSimuladas = new ArrayList<>();
    agendasSimuladas.add(new Agenda("CENTROSPENITENCIARIOS", "Provincia1", 123456789, "email1@example.com"));
    Page<Agenda> page = new PageImpl<>(agendasSimuladas);
    when(agendaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
    List<AgendaDto> agendaDtosSimulados = new ArrayList<>();
    agendaDtosSimulados.add(new AgendaDto("CENTROSPENITENCIARIOS", "Provincia1", 123456789, "email1@example.com"));
    when(modelMapper.map(agendasSimuladas.get(0), AgendaDto.class)).thenReturn(agendaDtosSimulados.get(0));
    AgendaParams params = new AgendaParams();
    params.setDepartments(DepartmentsEnum.CENTROSPENITENCIARIOS);
    params.setProvinces("Provincia1");
    params.setPhoneNumber(123456789);
    params.setEmail("email@example.com");

    Page<AgendaDto> result = agendaService.search(params);

    assertEquals(1, result.getTotalElements());
    assertEquals("CENTROSPENITENCIARIOS", result.getContent().get(0).getDepartments());
    assertEquals("Provincia1", result.getContent().get(0).getProvinces());
}
}
