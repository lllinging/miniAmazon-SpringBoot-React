package com.ecommerce.sportsceter.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import com.ecommerce.sportsceter.service.BasketService;
import java.util.List;
import java.util.stream.Collectors;


import com.ecommerce.sportsceter.entity.Basket;
import com.ecommerce.sportsceter.model.BasketResponse;
import com.ecommerce.sportsceter.model.BasketItemResponse;
import com.ecommerce.sportsceter.entity.BasketItem;
import com.ecommerce.sportsceter.respository.BasketRepository;



@Service
@Log4j2
public class BasketServiceImpl implements BasketService{
    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public List<BasketResponse> getAllBaskets() {
        log.info("Fetching all baskets");
        List<Basket> basketList = (List<Basket>)basketRepository.findAll();
        //use stream operator to map with Response
        List<BasketResponse> basketResponses = basketList.stream()
                .map(this::convertToBasketResponse)
                .collect(Collectors.toList());
        log.info("Fetched all baskets");
        return basketResponses;
    }

    @Override
    public BasketResponse getBasketById(String basketId) {
        log.info("fetching basket by Id: {}", basketId);
        //fetch all baskets
        Optional<Basket> basketOptional = basketRepository.findById(basketId);  
        // Basket basket = basketRepository.findById(basketId)
        //         .orElseThrow(() -> new RuntimeException("Basket not found"));
        //use stream operator to map with Response
        if(basketOptional.isPresent()){
            Basket basket = basketOptional.get();
            log.info("Fetched basket by Id: {}", basketId);
            return convertToBasketResponse(basket);
        } else {
            log.info("Basket with id: {} not found", basketId);
            return null;
        }
       
    }

    @Override
    public void deleteBasketById(String basketId) {
        log.info("Deleting basket by Id: {}", basketId);
        //fetch all baskets
        basketRepository.deleteById(basketId);
        log.info("Deleted basket by Id: {}", basketId);
    }

    @Override
    public BasketResponse createBasket(Basket basket) {
        log.info("Creating basket");
        //fetch all baskets
        Basket savedBasket = basketRepository.save(basket);
        //use stream operator to map with Response
        log.info("Basket created with id: {}",savedBasket.getId());
        return convertToBasketResponse(savedBasket);
    }

    private BasketResponse convertToBasketResponse(Basket basket) {
        if(basket == null) return null;
        List<BasketItemResponse> itemResponse = basket.getItems().stream()
                .map(this::convertToBasketItemResponse)
                .collect(Collectors.toList());
        return BasketResponse.builder()
                .id(basket.getId())
                .items(itemResponse)
                .build();
    }

    private BasketItemResponse convertToBasketItemResponse(BasketItem basketItem) {
        return BasketItemResponse.builder()
                .id(basketItem.getId())
                .name(basketItem.getName())
                .description(basketItem.getDescription())
                .price(basketItem.getPrice())
                .pictureUrl(basketItem.getPictureUrl())
                .productBrand(basketItem.getProductBrand())
                .productType(basketItem.getProductType())
                .quantity(basketItem.getQuantity())
                .build();
    }
}
