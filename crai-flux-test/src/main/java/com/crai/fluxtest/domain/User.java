package com.crai.fluxtest.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table("users")
public class User {

    @Id
    int id;
    String userName;
    String lastName;

    public User(String userName, String lastName) {
        this.userName = userName;
        this.lastName = lastName;
    }
}
