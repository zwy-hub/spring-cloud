package com.springcloud.controller;

import com.springcloud.entity.CommonResult;
import com.springcloud.entity.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果：" + result);
        if (result > 0)
            return new CommonResult(200, "success,serverPort："+serverPort, result);
        else
            return new CommonResult(444, "fail", null);
    }

    //localhost:8080/search/3
    @GetMapping("/search/{id}")
    public CommonResult search(@PathVariable("id") Long id) {
        Payment result = paymentService.getPaymentById(id);
        log.info("*****查找结果：" + result);
        if (result != null)
            return new CommonResult(200, "success,serverPort："+serverPort, result);
        return new CommonResult(444, "fail", null);
    }

    @RequestMapping("/test/lb")
    public String get(){
        return serverPort;
    }

    @RequestMapping("/payment/feign/timeout")
    public String timeout() {
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
