package com.ecommerce.sportsceter.respository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.sportsceter.entity.orderAggregate.Order;
import com.ecommerce.sportsceter.entity.orderAggregate.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBasketId(String basketId);    

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query ("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.itemOrdered.name LIKE %:productName%")
    List<Order> findByProductNameInOrderItems(@Param("productName") String productName);    

    @Query ("SELECT o FROM Order o where o.shippingAddress.city = :city")
    List<Order> findByShippingAddressCity(@Param("city") String city);

}
