package com.ecommerce.sportsceter.entity.orderAggregate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingAddress {
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zicode;
    private String country;

}
