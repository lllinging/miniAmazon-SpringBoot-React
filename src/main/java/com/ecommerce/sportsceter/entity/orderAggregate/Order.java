package com.ecommerce.sportsceter.entity.orderAggregate;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Column(name="Basket_Id")
    private String basketId;

    @Embedded
    private ShippingAddress shippingAddress;

    @Column(name="Order_Date")
    private LocalDateTime orderDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name="Sub_Total")
    private Double subTotal;

    @Column(name="Delivevry_Fee")
    private Long deliveryFee;

    @Enumerated(EnumType.STRING)
    @Column(name="Order_Status")
    private OrderStatus orderStatus = OrderStatus.Pending;

    public Double getTotal() {
        return getSubTotal() + getDeliveryFee();
    }

}
