package com.ecommerce.sportsceter.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sportsceter.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
    
}
