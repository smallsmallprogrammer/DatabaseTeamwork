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

    // Explicit getters/setters in case Lombok isn't active
    public Long getCountyId() { return this.countyId; }
    public void setCountyId(Long countyId) { this.countyId = countyId; }

    public String getCountyName() { return this.countyName; }
    public void setCountyName(String countyName) { this.countyName = countyName; }

    public Province getProvince() { return this.province; }
    public void setProvince(Province province) { this.province = province; }

    public Integer getDelistingYear() { return this.delistingYear; }
    public void setDelistingYear(Integer delistingYear) { this.delistingYear = delistingYear; }
}