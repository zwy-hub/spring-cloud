package com.springcloud.controller;

import com.springcloud.entity.CommonResult;
import com.springcloud.entity.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/zookeeper/test")
    public String testConnect(){
        return serverPort+" connect zookeeper success "+ UUID.randomUUID().toString();
    }

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

    @RequestMapping("/discovery")
    public Object discovery(){
        //services是微服务名称
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****服务名称："+service);
        }
        //微服务名称为CLOUD-PAYMENT-SERVICE的实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
}
