package com.swapna.collabeditor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "document_versions")
@EntityListeners(AuditingEntityListener.class)
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private File document;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private User editor;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "content_snapshot", nullable = false, columnDefinition = "TEXT")
    private String contentSnapshot;

    private String changeSummary;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
