package com.ecommerce.sportsceter.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.ecommerce.sportsceter.entity.orderAggregate.Order;
import com.ecommerce.sportsceter.model.OrderDto;
import com.ecommerce.sportsceter.model.OrderResponse;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "basketId", target = "basketId")
    @Mapping(source = "shippingAddress", target = "shippingAddress")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "deliveryFee", target = "deliveryFee")
    @Mapping(target = "total", expression = "java(order.getSubTotal() + order.getDeliveryFee())")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "orderStatus", constant = "Pending")
    OrderResponse orderToOrderResponse(Order order);

    @Mapping(target = "orderDate", expression = "java(orderDto.getOrderDate())")
    @Mapping(target = "orderStatus", constant = "Pending")
    Order orderResponseToOrder(OrderDto orderDto);  

    List<OrderDto> ordersToOrderResponses(List<Order> orders);      
    
    void updateOrderFromOrderResponse(OrderDto orderDto, @MappingTarget Order order);
}
