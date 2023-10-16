package com.crai.platform.event.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.crai.platform.starter.event.type.EventType;
import lombok.NoArgsConstructor;

@EventType
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    String uid;
    String name;
    String body;
}
