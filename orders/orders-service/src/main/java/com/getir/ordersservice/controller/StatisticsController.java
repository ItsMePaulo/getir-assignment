package com.getir.ordersservice.controller;

import com.getir.ordersservice.model.StatisticsDto;
import com.getir.ordersservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<Map<Month, StatisticsDto>> fetchStatistics(
            @RequestParam(value = "from", required = false)LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to
    ) {
        var fromDate = fetchFromDate(from);
        var toDate = fetchToDate(to);

        return ResponseEntity.ok(statisticsService.fetchGlobalStatistics(fromDate, toDate));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<Month, StatisticsDto>> fetchUserStatistics(
            @PathVariable("userId") UUID userId,
            @RequestParam(value = "from", required = false)LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to
    ) {

        var fromDate = fetchFromDate(from);
        var toDate = fetchToDate(to);

        return ResponseEntity.ok(statisticsService.fetchUserStatistics(userId, fromDate, toDate));
    }

    private LocalDateTime fetchFromDate(LocalDateTime from) {
        return  (from != null) ? from :
                LocalDate.of(1950, 1, 1).atStartOfDay();
    }

    private LocalDateTime fetchToDate(LocalDateTime to) {
        return (to != null) ? to : LocalDateTime.now();
    }
}
