package com.crai.platform.event.webbroker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crai.platform.event.webbroker.dto.AlertDto;
import com.crai.platform.event.webbroker.service.AlertService;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * TODO Aquí el frontal consultará las notificaciones y alertas
 * @author A421023
 *
 */
@RestController
@RequestMapping("event/alerts")
@Slf4j
public class CraiWebBrokerController {

    @Autowired
    AlertService alertService;
    
    @GetMapping
    public ResponseEntity<Page<AlertDto>> getAlerts(
          @Parameter(description = "Pagination number", name = "page")
          @RequestParam(defaultValue = "0", value="page", required = false) Integer page,
          @Parameter(description = "Pagination size ", name = "size")
          @RequestParam(defaultValue = "10", value="size", required = false) Integer size,
          @Parameter(description = "usuario", name = "user")
          @RequestParam(value="user", required = true) String user) {

       log.info("Petición de búsqueda: {} ", user);
       PageRequest pageRequest = PageRequest.of(page, size);
       return ResponseEntity.ok(alertService.findAll(pageRequest, user));
    }
}
