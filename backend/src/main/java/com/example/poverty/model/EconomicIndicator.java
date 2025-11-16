package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "economic_indicator")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EconomicIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id")
    private PovertyCounty county;

    private Double gdp;
    private Double gdpYoy;
    private Double gdpPerCapita;
    private Double ruralDisposableIncome;
    private Double ruralIncomeYoy;
    private Double fiscalRevenue;
    private Double fiscalRevenueYoy;
}