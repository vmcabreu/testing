package com.crai.platform.example.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String userName;
    String lastName;
    Integer age;

//    public User(String userName, String lastName, Integer age) {
//        this.userName = userName;
//        this.lastName = lastName;
//        this.age = age;
//    }
}
