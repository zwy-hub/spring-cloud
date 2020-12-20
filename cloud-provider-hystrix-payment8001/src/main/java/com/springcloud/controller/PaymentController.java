package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id) {
        return paymentService.payment_OK(id);
    }

    @RequestMapping("/payment/hystrix/timeout/{id}")
    public String timeout(@PathVariable("id") Integer id) {
        return paymentService.payment_Timeout(id);
    }

    @RequestMapping("/payment/circuitBreaker/{id}")
    public String paymentCircuitBreakerFallback(@PathVariable("id") Integer id){
        return paymentService.paymentCircuitBreaker(id);
    }
}
