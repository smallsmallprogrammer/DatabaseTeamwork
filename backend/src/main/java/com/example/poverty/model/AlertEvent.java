package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alert_event")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlertEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private AlertRule rule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private PovertyCounty county;

    private Integer year;
    private Double metricValue;
    private LocalDateTime triggeredAt;

    private Long acknowledgedBy;
    private LocalDateTime acknowledgedAt;
}