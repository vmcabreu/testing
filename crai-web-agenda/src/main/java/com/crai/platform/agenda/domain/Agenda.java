package com.crai.platform.agenda.domain;


import com.crai.platform.agenda.enums.DepartmentsEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Enumerated(EnumType.STRING)
    DepartmentsEnum departments;
    String provinces;
    long phoneNumber;
    @Email
    String email;

    public Agenda(String departamento2, String provincia2, int i, String mail) {
    }
}
