package com.crai.platform.event.webbroker.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class VictimEntity {

   @ManyToOne
   @Id
   private String victimId;     
   private String riskLevel;     
   private Integer victimPriority;     
   private String geolocation;     
   private Long birthdayDate; 
}