package com.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "CLOUD-PAYMENT-SERVICE",fallback = PaymentServiceFallback.class)
@Component
public interface PaymentService {
    @RequestMapping("/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id);

    @RequestMapping("/payment/hystrix/timeout/{id}")
    public String timeout(@PathVariable("id") Integer id);
}
