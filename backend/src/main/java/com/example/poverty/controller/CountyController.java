package com.example.poverty.controller;

import com.example.poverty.model.EconomicIndicator;
import com.example.poverty.model.PovertyCounty;
import com.example.poverty.repository.EconomicIndicatorRepository;
import com.example.poverty.repository.PovertyCountyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/counties")
public class CountyController {
    private final PovertyCountyRepository countyRepo;
    private final EconomicIndicatorRepository indicatorRepo;

    public CountyController(PovertyCountyRepository countyRepo, EconomicIndicatorRepository indicatorRepo) {
        this.countyRepo = countyRepo; this.indicatorRepo = indicatorRepo;
    }

    @GetMapping
    public List<PovertyCounty> list(@RequestParam(required = false) Long provinceId) {
        if (provinceId == null) return countyRepo.findAll();
        return countyRepo.findByProvince_ProvinceId(provinceId);
    }

    @GetMapping("/{id}/indicators")
    public List<EconomicIndicator> getIndicators(@PathVariable Long id,
                                                 @RequestParam(required = false) Integer from,
                                                 @RequestParam(required = false) Integer to) {
        Optional<PovertyCounty> c = countyRepo.findById(id);
        if (!c.isPresent()) return List.of();
        int f = (from == null) ? 2015 : from;
        int t = (to == null) ? java.time.Year.now().getValue() : to;
        return indicatorRepo.findByCountyAndYearBetweenOrderByYearAsc(c.get(), f, t);
    }
}