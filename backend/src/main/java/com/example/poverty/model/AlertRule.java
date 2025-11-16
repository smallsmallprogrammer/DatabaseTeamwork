package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alert_rule")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AlertRule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    private String ruleName;
    private String metricKey;
    private String comparator;
    private Double threshold;
    private Integer durationYears;
    private Boolean enabled;
}