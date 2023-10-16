package com.crai.platform.agenda.params;

import com.crai.platform.agenda.enums.DepartmentsEnum;
import com.crai.starter.jpa.params.SearchParams;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgendaParams extends SearchParams {
    @Schema(description = "id")
    Long id;
    @Schema(description = "departments")
    DepartmentsEnum departments;
    @Schema(description = "provinces")
    String provinces;
    @Schema(description = "phone_number")
    long phoneNumber;
    @Schema(description = "email")
    String email;


}
