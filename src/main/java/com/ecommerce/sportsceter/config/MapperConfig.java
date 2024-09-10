package com.ecommerce.sportsceter.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.sportsceter.mapper.OrderMapper;

@Configuration
public class MapperConfig {
    @Bean
    public OrderMapper orderMapper(){
        return Mappers.getMapper(OrderMapper.class);
    }   
}
