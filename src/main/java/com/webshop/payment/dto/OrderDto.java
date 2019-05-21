package com.webshop.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private List<Product> products = new ArrayList<>();
    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String id;
        private Integer number;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Long id;
    }

}
