package com.crai.platform.event.webbroker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.crai.platform.event.webbroker.dto.AlertDto;

public interface AlertService {
   
   /**
    * Devuelve todas las alertas de un usuario
    * @param pageRequest
    * @param operatorUser
    * @return
    */
   public Page<AlertDto> findAll(final PageRequest pageRequest, String operatorUser);

   /**
    * Crea una alerta
    * @param alert
    */
   public void createAlert(AlertDto alert);
   
   /**
    * Busca por Id una alerta
    * @param id
    * @return
    */
   public AlertDto findById(String id);
   
}