package com.crai.platform.event.webbroker.service;

import com.crai.platform.event.webbroker.dto.AlertDto;

public interface ProcessAlertService {
   
   public String process(AlertDto alert);
}