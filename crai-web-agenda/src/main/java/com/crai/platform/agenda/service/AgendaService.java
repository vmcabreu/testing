package com.crai.platform.agenda.service;

import com.crai.platform.agenda.dto.AgendaDto;
import com.crai.platform.agenda.params.AgendaParams;
import com.crai.platform.agenda.repository.AgendaRepository;
import com.crai.platform.agenda.specifications.AgendaSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {
    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    ModelMapper modelmapper;

    public Page<AgendaDto> search(AgendaParams params){
       AgendaParams agendaParams = params ==null ? new AgendaParams(): params;
        AgendaSpecification agendaSpecification = new AgendaSpecification(agendaParams);
        return agendaRepository.findAll(agendaSpecification,agendaSpecification.getUpdatable())
                .map(agenda -> modelmapper.map(agenda,AgendaDto.class));

    }

}
