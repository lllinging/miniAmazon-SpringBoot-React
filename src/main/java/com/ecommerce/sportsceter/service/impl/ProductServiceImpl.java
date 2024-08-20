package com.ecommerce.sportsceter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecommerce.sportsceter.entity.Product;
import com.ecommerce.sportsceter.model.ProductResponse;
import com.ecommerce.sportsceter.respository.ProductRepository;
import com.ecommerce.sportsceter.service.ProductService;
import com.ecommerce.sportsceter.exceptions.ProductNotFoundException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Integer productId){   
        log.info("fetching product by Id: {}", productId);

        //fetch all products
        Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException("Product doesn't exist")); //new RuntimeException("Product not found"));
        //use stream operator to map with Response
        ProductResponse productResponse = convertToProductResponse(product);
        log.info("Fetched product by Id: {}", productId);
        return productResponse; 
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, String keyword, Integer brandId, Integer typeId){
        log.info("Fetching products");
        Specification<Product> spec = Specification.where(null);
        if(brandId != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("id"), brandId));
        }
        if(typeId != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type").get("id"), typeId));
        }
        if(keyword != null && !keyword.isEmpty()){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
        }

        //fetch all products
        Page<Product> productList = productRepository.findAll(spec, pageable);
        //map
        Page<ProductResponse> productResponses = productList
                .map(this::convertToProductResponse); 
        log.info("Fetched all products");
        return productResponses; 
    }

    // public List<ProductResponse> getProducts(){
    //     log.info("Fetching products");

    //     //fetch all products
    //     List<Product> productList = productRepository.findAll();
    //     //use stream operator to map with Response
    //     List<ProductResponse> productResponses = productList.stream()
    //             .map(this::convertToProductResponse) 
    //             .collect(Collectors.toList());
    //     log.info("Fetched all products");
    //     return productResponses; 
    // }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productBrand(product.getBrand().getName())
                .productType(product.getType().getName())
                .build();
    }

}
