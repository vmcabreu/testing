package com.crai.platform.watchlist.domain;

import com.crai.platform.watchlist.enums.AlertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "watchlist")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id")
    List<Column> columns;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id")
    List<Operation> operations;

    String name;

    String title;

    //Topic
    String workElement;

    @Enumerated(EnumType.STRING)
    AlertStatus status;

    LocalDateTime createdWatchlist;
    String userRoles;

}
