package com.example.poverty.repository;

import com.example.poverty.model.EconomicIndicator;
import com.example.poverty.model.PovertyCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EconomicIndicatorRepository extends JpaRepository<EconomicIndicator, Long> {
    List<EconomicIndicator> findByCountyAndYearBetweenOrderByYearAsc(PovertyCounty county, Integer start, Integer end);
    List<EconomicIndicator> findByCounty_OrderByYearAsc(PovertyCounty county);
    List<EconomicIndicator> findByYear(Integer year);
}