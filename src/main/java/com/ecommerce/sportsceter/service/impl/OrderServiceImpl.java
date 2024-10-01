package com.ecommerce.sportsceter.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.sportsceter.entity.Basket;
import com.ecommerce.sportsceter.entity.orderAggregate.Order;
import com.ecommerce.sportsceter.entity.orderAggregate.OrderItem;
import com.ecommerce.sportsceter.entity.orderAggregate.ProductItemOrdered;
import com.ecommerce.sportsceter.mapper.OrderMapper;
import com.ecommerce.sportsceter.model.BasketItemResponse;
import com.ecommerce.sportsceter.model.BasketResponse;
import com.ecommerce.sportsceter.model.OrderDto;
import com.ecommerce.sportsceter.model.OrderResponse;
import com.ecommerce.sportsceter.respository.BrandRepository;
import com.ecommerce.sportsceter.respository.OrderRepository;
import com.ecommerce.sportsceter.respository.TypeRepository;
import com.ecommerce.sportsceter.service.BasketService;
import com.ecommerce.sportsceter.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final BrandRepository brandRepository;
    private final TypeRepository typeRepository;
    private final BasketService basketService;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, BrandRepository brandRepository,
            TypeRepository typeRepository, BasketService basketService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.brandRepository = brandRepository;
        this.typeRepository = typeRepository;
        this.basketService = basketService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse getOrderById(Integer orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(orderMapper::orderToOrderResponse).orElse(null);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
       List<Order> orders = orderRepository.findAll();
       return orders.stream().map(orderMapper::orderToOrderResponse).collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::orderToOrderResponse);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
        
    }

    @Override
    public Integer createOrder(OrderDto orderDto) {
        //Fetchig basket details
        BasketResponse basketResponse = basketService.getBasketById(orderDto.getBasketId());
        if (basketResponse == null) {
            log.error("Basket with id: {} not found", orderDto.getBasketId());
            return null;
        }
        //Mapping basket items to order items
        List<OrderItem> orderItems = basketResponse.getItems().stream()
                .map(this::mapBasketItemToOrderItem)
                .collect(Collectors.toList());
        //Calculating total price
        double subTotal = basketResponse.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum(); 
        //Setting order details
        Order order = orderMapper.orderResponseToOrder(orderDto);
        order.setOrderItems(orderItems);
        order.setSubTotal(subTotal);
        order.setOrderDate(LocalDateTime.now());
        //Saving the order 
        Order savedOrder = orderRepository.save(order);
        basketService.deleteBasketById(orderDto.getBasketId());
        //Returning the response
        return savedOrder.getId();
    }

    private OrderItem mapBasketItemToOrderItem(BasketItemResponse basketItemResponse) {
        if (basketItemResponse != null) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemOrdered(mapBasketItemToProduct(basketItemResponse));
            orderItem.setQuantity(basketItemResponse.getQuantity());
            return orderItem;
        } else {
            return null;
        }
    }

    private ProductItemOrdered mapBasketItemToProduct(BasketItemResponse basketItemResponse) {
        ProductItemOrdered productItemOrdered = new ProductItemOrdered();
        productItemOrdered.setName(basketItemResponse.getName());
        productItemOrdered.setPictureUrl(basketItemResponse.getPictureUrl());
        productItemOrdered.setProductId(basketItemResponse.getId());
        return productItemOrdered;
    }

}
