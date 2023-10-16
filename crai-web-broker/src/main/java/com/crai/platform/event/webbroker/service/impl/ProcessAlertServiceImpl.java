package com.crai.platform.event.webbroker.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.crai.platform.event.webbroker.dto.AlertDto;
import com.crai.platform.event.webbroker.service.AlertService;
import com.crai.platform.event.webbroker.service.ProcessAlertService;

@Component
@Slf4j
public class ProcessAlertServiceImpl implements ProcessAlertService {

   @Autowired
   AlertService alertService;
   
      @Override
   public String process(AlertDto alert) {
      
      String routingKey = "alertas";
      // TODO esto habrá que cambiarlo
      if (alert.getVictim() != null && alert.getVictim().getVictimPriority() != null) {
         routingKey += "." + alert.getVictim().getVictimPriority();
      } else { // prioridad por defecto
         routingKey += ".3";
      }
      // TODO compruebo si la alerta existe en bbdd, si no, la creo.
      AlertDto alertSaved = alertService.findById(alert.getAlertId());
      if (alertSaved == null) { // La alerta no existe
         alertService.createAlert(alert);
      } else { // La alerta ya existe
         if (!StringUtils.isEmpty(alertSaved.getOperatorUser())) { // Tiene usuario asignado
            routingKey += "." + alertSaved.getOperatorUser();
         }
      }
      // TODO consulto en bbdd si tengo que modificar la prioridad de la alerta
      // TODO persisto el dato en bbdd
      return routingKey;
   }
}