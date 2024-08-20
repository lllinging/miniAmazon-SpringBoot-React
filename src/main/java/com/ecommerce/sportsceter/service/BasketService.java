package com.ecommerce.sportsceter.service;

import java.util.List;
import com.ecommerce.sportsceter.model.BasketResponse;
import com.ecommerce.sportsceter.entity.Basket;

public interface BasketService {
    List<BasketResponse> getAllBaskets();   
    BasketResponse getBasketById(String basketId);
    void deleteBasketById(String BasketId);
    BasketResponse createBasket(Basket basket);
}
