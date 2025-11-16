package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agricultural_output")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AgriculturalOutput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private PovertyCounty county;
    private Double totalAgriOutput;
    private Double grainOutput;
    private Double riceOutput;
    private Double cornOutput;
}