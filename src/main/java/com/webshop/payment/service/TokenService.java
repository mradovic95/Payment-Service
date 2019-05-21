package com.webshop.payment.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    Claims parseToken(String jwt);

}
