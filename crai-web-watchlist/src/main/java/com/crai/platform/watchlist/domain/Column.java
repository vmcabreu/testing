package com.crai.platform.watchlist.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "col")
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long columnId;

    String field;

    String header;

    String type;

    LocalDateTime createdCol;

}
