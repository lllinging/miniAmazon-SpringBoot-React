package com.ecommerce.sportsceter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.ecommerce.sportsceter.entity.BasketItem;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketResponse {
    private String id;
    private List<BasketItemResponse> items;     
    
}
