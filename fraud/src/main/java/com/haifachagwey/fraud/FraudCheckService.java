package com.haifachagwey.fraud;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository repository;

    public FraudCheckService(FraudCheckHistoryRepository repository) {
        this.repository = repository;
    }

    public boolean isFraudulentCustomer(Integer customerId) {
        repository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }
}
