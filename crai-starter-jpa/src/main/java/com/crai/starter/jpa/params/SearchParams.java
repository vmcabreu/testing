package com.crai.starter.jpa.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with attributes for searchs
 * @author ATOS
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true, includeFieldNames = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchParams extends PaginationParams {

  private static final long serialVersionUID = 1L;

  @Schema(description = "Join search clauses by OR or AND.")
  private String searchMode = "AND";

  @Schema(description = "List of attributes to sort")
  private List<String> sort = new ArrayList<>();
}
