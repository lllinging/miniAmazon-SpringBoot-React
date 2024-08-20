package com.ecommerce.sportsceter.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ecommerce.sportsceter.model.ProductResponse;

public interface ProductService {
    ProductResponse getProductById(Integer productId);
    Page<ProductResponse> getProducts(Pageable pageable, String keyword, Integer brandId, Integer typeId);
    // List<ProductResponse> getProducts();
}
