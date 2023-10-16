package com.crai.platform.watchlist.params;

import com.crai.platform.watchlist.enums.AlertStatus;
import com.crai.starter.jpa.params.SearchParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WatchlistParams extends SearchParams {


    @Schema(description = "id")
    Long id;

    @Schema(description = "name")
    String name;

    @Schema(description = "WorkElement")
    String WorkElement;

    @Schema(description = "status")
    AlertStatus status;

    @Schema(description = "createdWatchlist")
    LocalDateTime createdWatchlist;

    @Schema(description = "roles")
    String userRoles;
}
