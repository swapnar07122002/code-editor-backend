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
@Table(name = "project_collaborators",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id","user_id"})
        }
      )
@EntityListeners(AuditingEntityListener.class)
public class ProjectCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Link to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "permission", nullable = false)
    private String permission = "read"; // default value

    @CreatedDate
    @Column(name = "added_at", updatable = false, insertable = false)
    private LocalDateTime addedAt;
}
