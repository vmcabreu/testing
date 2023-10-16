package com.crai.platform.event.webbroker.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AlertEntity {

   @Id
   private String alertId;     
   private String alertIotTypeCode;     
   private Integer proximity;     
   private Long alarmDate;     
   private String deviceId;     
   private String deviceType;
   private VictimEntity victim;     
   private AccusedEntity accused;
   private String operatorUser;   
}