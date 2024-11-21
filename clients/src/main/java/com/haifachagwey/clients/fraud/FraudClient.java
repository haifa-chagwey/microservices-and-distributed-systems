package com.haifachagwey.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @FeignClient(name="fraud", path = "api/v1/fraud-check")
// @FeignClient(name="fraud", url = "http://localhost:8081")
@FeignClient(name="fraud", url = "${clients.fraud.url}")
public interface FraudClient {
    @GetMapping("api/v1/fraud-check/{customerId}")
    FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId);
}
