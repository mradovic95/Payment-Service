package com.webshop.payment.controller;

import com.webshop.payment.dto.PaymentRequest;
import com.webshop.payment.secutiry.CheckSecurity;
import com.webshop.payment.service.impl.PaymentServiceImpl;
import com.webshop.payment.service.impl.PaypalService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl paymentService;
    private final PaypalService paypalService;

    @ApiOperation(value = "Payment articles")
    @PostMapping(value = "/make/payment")
    @CheckSecurity(roles = "ROLE_USER")
    public Map<String, Object> pay(@RequestHeader("Authorization") String authorization
            , @RequestBody @Valid PaymentRequest paymentRequest, @RequestParam("sum") String totalCost) {

        paymentService.pay(paymentRequest, authorization);
        return paypalService.createPayment(totalCost);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request) {
        System.out.println(request.getParameter("paymentId"));
        System.out.println(request.getParameter("token"));
        System.out.println(request.getParameter("PayerID"));
        return paypalService.completePayment(request);
    }

}
