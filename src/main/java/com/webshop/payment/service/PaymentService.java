package com.webshop.payment.service;

import com.webshop.payment.dto.PaymentRequest;

public interface PaymentService {

    void pay(PaymentRequest paymentRequest, String id);

}
