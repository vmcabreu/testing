package com.crai.platform.event.webbroker.service.impl;

import java.util.Optional;
import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.crai.platform.event.webbroker.component.GenericMapper;
import com.crai.platform.event.webbroker.dto.AlertDto;
import com.crai.platform.event.webbroker.dto.AlertEntity;
import com.crai.platform.event.webbroker.repository.AlertRepository;
import com.crai.platform.event.webbroker.service.AlertService;

@Component
public class AlertServiceImpl implements AlertService {

   @Autowired
   AlertRepository repo;

   GenericMapper<AlertEntity, AlertDto> mapper = new GenericMapper<AlertEntity, AlertDto>(new ModelMapper(), AlertEntity.class, AlertDto.class);
   
   private Function<Page<AlertEntity>, Page<AlertDto>> toDTOPage = entities -> entities.map(mapper::toDTO);
   
   @Override
   public void createAlert(AlertDto alert) {
      repo.save(mapper.toEntity(alert));
   }
   
   @Override
   public Page<AlertDto> findAll(PageRequest pageRequest, String operatorUser) {
      
      Page<AlertDto> dtoList = Optional.ofNullable(repo.findAllByOperatorUser(operatorUser, pageRequest))
            .map(toDTOPage)
            .orElse(Page.empty());      
      return dtoList;
   }

   @Override
   public AlertDto findById(String id) {
      AlertDto res = Optional.ofNullable(repo.findById(id)).map(mapper::toDTO).orElse(null);
      return res;
   }
}