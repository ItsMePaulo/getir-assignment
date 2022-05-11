package com.getir.ordersapi.clients;


import com.getir.ordersapi.model.StatisticsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.UUID;

@FeignClient(value = "statistics", url = "${orders.url:localhost:8002/orders}", primary = false)
public interface StatisticsClient {

    @GetMapping
     ResponseEntity<Map<Month, StatisticsDto>> fetchStatistics(
            @RequestParam(value = "from", required = false) LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to
    );

    @GetMapping("/{userId}")
     ResponseEntity<Map<Month, StatisticsDto>> fetchUserStatistics(
            @PathVariable("userId") UUID userId,
            @RequestParam(value = "from", required = false)LocalDateTime from,
            @RequestParam(value = "to", required = false) LocalDateTime to
    );
}
