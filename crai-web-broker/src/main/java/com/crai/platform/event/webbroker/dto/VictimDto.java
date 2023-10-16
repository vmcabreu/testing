package com.crai.platform.event.webbroker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VictimDto {
   
   private String victimId;     
   private String riskLevel;     
   private Integer victimPriority;     
   private String geolocation;     
   private Long birthdayDate; 
} 