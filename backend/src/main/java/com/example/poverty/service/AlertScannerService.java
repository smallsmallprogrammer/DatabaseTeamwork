package com.example.poverty.service;

import com.example.poverty.model.*;
import com.example.poverty.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertScannerService {

    private final AlertRuleRepository ruleRepo;
    private final EconomicIndicatorRepository indicatorRepo;
    private final AlertEventRepository eventRepo;
    private final PovertyCountyRepository countyRepo;

    public AlertScannerService(AlertRuleRepository ruleRepo, EconomicIndicatorRepository indicatorRepo,
                               AlertEventRepository eventRepo, PovertyCountyRepository countyRepo) {
        this.ruleRepo = ruleRepo;
        this.indicatorRepo = indicatorRepo;
        this.eventRepo = eventRepo;
        this.countyRepo = countyRepo;
    }

    @Transactional
    public void scanAllRules() {
        List<AlertRule> rules = ruleRepo.findByEnabledTrue();
        for (AlertRule r : rules) {
            List<PovertyCounty> all = countyRepo.findAll();
            for (PovertyCounty c : all) {
                evaluateRuleForCounty(r, c);
            }
        }
    }

    private void evaluateRuleForCounty(AlertRule rule, PovertyCounty county) {
        int N = (rule.getDurationYears()==null || rule.getDurationYears()<=0) ? 1 : rule.getDurationYears();
        List<EconomicIndicator> list = indicatorRepo.findByCounty_OrderByYearAsc(county);
        if (list == null || list.size() == 0) return;
        int size = list.size();
        if (size < N) return;
        boolean allMatch = true;
        for (int i = size - N; i < size; i++) {
            EconomicIndicator e = list.get(i);
            Double metricVal = extractMetric(e, rule.getMetricKey());
            if (metricVal == null) {
                allMatch = false; break;
            }
            if (!applyComparator(metricVal, rule.getComparator(), rule.getThreshold())) {
                allMatch = false; break;
            }
        }
        if (allMatch) {
            EconomicIndicator last = list.get(size-1);
            AlertEvent ev = AlertEvent.builder()
                    .rule(rule)
                    .county(county)
                    .year(last.getYear())
                    .metricValue(extractMetric(last, rule.getMetricKey()))
                    .triggeredAt(LocalDateTime.now())
                    .build();
            eventRepo.save(ev);
        }
    }

    private Double extractMetric(EconomicIndicator e, String metricKey) {
        if (metricKey == null) return null;
        switch (metricKey) {
            case "gdpYoy": return e.getGdpYoy();
            case "ruralDisposableIncome": return e.getRuralDisposableIncome();
            case "fiscalRevenueYoy": return e.getFiscalRevenueYoy();
            case "gdp": return e.getGdp();
            default: return null;
        }
    }

    private boolean applyComparator(Double val, String comp, Double threshold) {
        if (val == null || comp == null || threshold == null) return false;
        switch (comp) {
            case "lt": return val < threshold;
            case "lte": return val <= threshold;
            case "gt": return val > threshold;
            case "gte": return val >= threshold;
            default: return false;
        }
    }
}