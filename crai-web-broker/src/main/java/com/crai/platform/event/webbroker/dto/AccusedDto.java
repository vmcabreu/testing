package com.crai.platform.event.webbroker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccusedDto {
   private String accusedId;     
   private String geolocation;
}
