package com.crai.platform.event.webbroker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto {
   
   private String alertId;     
   private String alertIotTypeCode;     
   private Integer proximity;     
   private Long alarmDate;     
   private String deviceId;     
   private String deviceType;     
   private VictimDto victim;     
   private AccusedDto accused; 
   private String operatorUser;
}
