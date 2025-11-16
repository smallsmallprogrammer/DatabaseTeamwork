package com.example.poverty.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "poverty_county")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PovertyCounty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countyId;

    @Column(nullable = false, length = 100)
    private String countyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Province province;

    private Integer delistingYear;
}