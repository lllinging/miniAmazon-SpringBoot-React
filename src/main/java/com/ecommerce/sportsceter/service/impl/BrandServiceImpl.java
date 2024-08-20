package com.ecommerce.sportsceter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.sportsceter.entity.Brand;
import com.ecommerce.sportsceter.model.BrandResponse;
import com.ecommerce.sportsceter.respository.BrandRepository;
import com.ecommerce.sportsceter.service.BrandService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BrandServiceImpl  implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandResponse> getAllBrands(){
        log.info("Getting all brands");

        //fetch all brands
        List<Brand> brandList = brandRepository.findAll();
        //use stream operator to map with Response
        List<BrandResponse> brandResponses = brandList.stream()
                .map(this::convertToBrandResponse) 
                .collect(Collectors.toList());
        log.info("Fetched all brands");
        return brandResponses; 
    }

    private BrandResponse convertToBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
