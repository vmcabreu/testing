package com.crai.platform.agenda.dto;

import com.crai.platform.agenda.domain.Agenda;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AgendaDto {
    String departments;
    String provinces;
    long phoneNumber;
    String email;
}
