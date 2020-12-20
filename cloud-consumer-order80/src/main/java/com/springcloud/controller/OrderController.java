package com.springcloud.controller;

import com.springcloud.entity.CommonResult;
import com.springcloud.entity.Payment;
import com.springcloud.lb.MyLB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class OrderController {
    //    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private MyLB myLB;

    @GetMapping("/consumer/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/create", payment, CommonResult.class);
    }

    //  localhost/consumer/search/1   消费者不应该写上端口
    @GetMapping("/consumer/search/{id}")
    public CommonResult<Payment> search(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/search/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/searchEntity/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/search/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            log.info(entity.getHeaders().toString());
            return entity.getBody();
        } else {
            return new CommonResult(444, "fail", null);
        }
    }

    @RequestMapping("/consumer/test/lb")
    public String getPaymentLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null){
            return null;
        }
        ServiceInstance serviceInstance = myLB.instances(instances);
        return restTemplate.getForObject(serviceInstance.getUri()+"/test/lb",String.class);
    }
}
