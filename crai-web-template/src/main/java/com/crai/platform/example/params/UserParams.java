package com.crai.platform.example.params;


import com.crai.starter.jpa.params.SearchParams;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserParams extends SearchParams {

    @Schema(description = "id")
    Long id;
    @Schema (description = "userName")
    String userName;
    @Schema (description = "lastName")
    String lastName;
    @Schema (description = "lastName")
    @Valid
    @NotNull
            @Min(15)
    Integer age;
}
