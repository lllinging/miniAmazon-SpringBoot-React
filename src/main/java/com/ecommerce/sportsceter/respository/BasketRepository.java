package com.ecommerce.sportsceter.respository;

import org.springframework.stereotype.Repository;   
import org.springframework.data.repository.CrudRepository;
import com.ecommerce.sportsceter.entity.Basket;


@Repository
public interface BasketRepository extends CrudRepository<Basket, String>{
    
}
