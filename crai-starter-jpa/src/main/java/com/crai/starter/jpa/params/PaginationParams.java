package com.crai.starter.jpa.params;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PaginationParams implements Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "Number of page in pagination mode")
  int page = 0;

  @Schema(description = "Size of pagination")
  int pageSize = 20;

}
