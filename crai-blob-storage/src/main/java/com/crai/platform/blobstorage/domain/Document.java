package com.crai.platform.blobstorage.domain;

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
@Table(name = "document", schema = "public")
public class Document {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "document_id")
        private Long documentId;

        @Column(name = "file_name", length = 255)
        private String fileName;

        @Column(name = "file_path", length = 255)
        private String filePath;

        @Column(name = "visible", length = 255)
        private Boolean visible;

        @ManyToOne
        @JoinColumn(name = "work_unit_id")
        private WorkUnit workUnit;
}