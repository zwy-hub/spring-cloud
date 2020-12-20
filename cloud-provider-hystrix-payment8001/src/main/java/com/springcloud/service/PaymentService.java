package com.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    String payment_OK(Integer id);

    String payment_Timeout(Integer id);

    String paymentCircuitBreaker(Integer id);

    String paymentCircuitBreakerFallback(Integer id);
}
