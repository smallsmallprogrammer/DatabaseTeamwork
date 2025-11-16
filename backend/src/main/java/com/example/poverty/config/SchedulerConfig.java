package com.example.poverty.config;

import com.example.poverty.service.AlertScannerService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerConfig {
    private final AlertScannerService scannerService;

    public SchedulerConfig(AlertScannerService scannerService) { this.scannerService = scannerService; }

    @Scheduled(cron = "0 0 2 * * ?")
    public void dailyScan() {
        scannerService.scanAllRules();
    }
}