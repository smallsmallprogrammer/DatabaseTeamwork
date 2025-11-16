package com.example.poverty.controller;

import com.example.poverty.model.AlertEvent;
import com.example.poverty.model.AlertRule;
import com.example.poverty.repository.AlertEventRepository;
import com.example.poverty.repository.AlertRuleRepository;
import com.example.poverty.service.AlertScannerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertRuleRepository ruleRepo;
    private final AlertEventRepository eventRepo;
    private final AlertScannerService scannerService;

    public AlertController(AlertRuleRepository ruleRepo, AlertEventRepository eventRepo, AlertScannerService scannerService) {
        this.ruleRepo = ruleRepo; this.eventRepo = eventRepo; this.scannerService = scannerService;
    }

    @GetMapping("/rules")
    public List<AlertRule> listRules() {
        return ruleRepo.findAll();
    }

    @PostMapping("/rules")
    public AlertRule createRule(@RequestBody AlertRule r) {
        if (r.getEnabled() == null) r.setEnabled(true);
        return ruleRepo.save(r);
    }

    @GetMapping
    public List<AlertEvent> listEvents() {
        return eventRepo.findAll();
    }

    @PostMapping("/scan")
    public String scanNow() {
        scannerService.scanAllRules();
        return "scanned";
    }
}