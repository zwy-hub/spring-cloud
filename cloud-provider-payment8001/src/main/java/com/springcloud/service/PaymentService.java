package com.springcloud.service;

import org.apache.ibatis.annotations.Param;
import com.springcloud.entity.Payment;

public interface PaymentService {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
