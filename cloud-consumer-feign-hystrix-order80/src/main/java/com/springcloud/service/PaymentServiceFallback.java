package com.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {
    public String ok(Integer id) {
        return "method ok fall back /(ㄒoㄒ)/~~";
    }

    public String timeout(Integer id) {
        return "method timeout fall back /(ㄒoㄒ)/~~";
    }
}
