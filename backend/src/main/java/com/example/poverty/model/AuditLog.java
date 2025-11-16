package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
    private Long userId;
    private String action;
    private String targetType;
    private String targetId;
    @Column(columnDefinition = "TEXT")
    private String details;
    private LocalDateTime createdAt;
}