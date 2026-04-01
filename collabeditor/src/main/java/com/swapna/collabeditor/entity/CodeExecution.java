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
@Table(name = "code_executions")
@EntityListeners(AuditingEntityListener.class)
public class CodeExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private File document;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "input",columnDefinition = "TEXT")
    private String input;

    @Column(name = "output",columnDefinition = "TEXT")
    private String output;

    @Column(name = "errors",columnDefinition = "TEXT")
    private String errors;

    @Column(name = "status",nullable = false)
    private String status; // success, error, running

    @CreatedDate
    @Column(name = "executed_at", updatable = false)
    private LocalDateTime executedAt;
}
