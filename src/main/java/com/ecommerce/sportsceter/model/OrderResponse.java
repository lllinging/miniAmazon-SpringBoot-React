package com.ecommerce.sportsceter.model;

import java.time.LocalDateTime;

import com.ecommerce.sportsceter.entity.orderAggregate.OrderStatus;
import com.ecommerce.sportsceter.entity.orderAggregate.ShippingAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Integer id;
    private String basketId;
    private ShippingAddress shippingAddress;
    private Long subTotal;
    private Long deliveryFee;
    private Double total;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
