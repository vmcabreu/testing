package com.crai.platform.blobstorage.domain;

import com.crai.platform.blobstorage.enums.WorkUnitTypeEnum;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//cambiar a el schema que se haya definido
@Table(name = "work_unit", schema = "public")
public class WorkUnit {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "work_unit_id")
        private Long workUnitId;

        @Column(name = "name", length = 255, nullable = false)
        private String name;

        @Enumerated(EnumType.STRING)
        @Column(name = "work_unit_type", length = 20, nullable = false)
        private WorkUnitTypeEnum workUnitType;

        @ManyToOne
        @JoinColumn(name = "parent_unit_id")
        private WorkUnit parentUnit;
}