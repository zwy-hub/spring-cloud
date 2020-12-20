package com.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/consumer/hystrix/ok/{id}")
    public String ok(@PathVariable("id") Integer id){
        return paymentService.ok(id);
    }
    @HystrixCommand(fallbackMethod = "payment_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    })
//    @HystrixCommand
    @RequestMapping("/consumer/hystrix/timeout/{id}")
    public String timeout(@PathVariable("id") Integer id){
        return paymentService.timeout(id);
    }
    public String payment_TimeoutHandler(Integer id) {
        return " 等太久了，我不想等了！   /(ㄒoㄒ)/~~";
    }
    public String payment_Global_FallbackMethod() {
        return "Global等太久了，我不想等了！   /(ㄒoㄒ)/~~";
    }
}
