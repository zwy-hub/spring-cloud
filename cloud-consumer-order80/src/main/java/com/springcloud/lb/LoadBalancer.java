package com.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    //根据服务list返回分配给客户端的某一个服务
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
