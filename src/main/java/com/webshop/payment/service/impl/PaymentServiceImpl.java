package com.webshop.payment.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webshop.payment.dto.OrderDto;
import com.webshop.payment.dto.PaymentRequest;
import com.webshop.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private JmsTemplate jmsTemplate;
    private ObjectMapper objectMapper;
    private String orderQueue;

    public PaymentServiceImpl(JmsTemplate jmsTemplate, ObjectMapper objectMapper
            , @Value("${destination.order}") String orderQueue) {

        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.orderQueue = orderQueue;
    }

    @Override
    public void pay(PaymentRequest paymentRequest, String id) {
        log.info("New payment request: {}", paymentRequest);

        OrderDto orderDto = new OrderDto();
        orderDto.setProducts(paymentRequest.getProducts().stream()
                .map((product -> new OrderDto.Product(product.getId(), product.getNumber())))
                .collect(Collectors.toList()));
        orderDto.setUser(new OrderDto.User(Long.parseLong(id)));

        try {
            String message = objectMapper.writeValueAsString(orderDto);
            jmsTemplate.send(orderQueue, session -> session.createTextMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
