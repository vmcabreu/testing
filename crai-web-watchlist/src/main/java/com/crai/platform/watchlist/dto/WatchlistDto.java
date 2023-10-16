package com.crai.platform.watchlist.dto;

import com.crai.platform.watchlist.domain.Column;
import com.crai.platform.watchlist.domain.Operation;
import com.crai.platform.watchlist.enums.AlertStatus;
import com.crai.starter.jpa.util.StringListConverter;
import jakarta.persistence.Convert;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class WatchlistDto {
    String name;
    String workElement;
    String title;
    LocalDateTime createdWatchlist;
    List<Operation> operations;
    List<Column> columns;
    AlertStatus status;
    String userRoles;

}
