package com.ecommerce.sportsceter.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.sportsceter.entity.Type;
import com.ecommerce.sportsceter.model.TypeResponse;
import com.ecommerce.sportsceter.respository.TypeRepository;
import com.ecommerce.sportsceter.service.TypeService;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class TypeServiceImpl implements TypeService{
    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public List<TypeResponse> getAllTypes(){
        log.info("Getting all types");

        //fetch all types
        List<Type> typeList = typeRepository.findAll();
        //use stream operator to map with Response
        List<TypeResponse> typeResponses = typeList.stream()
                .map(this::convertToTypeResponse) 
                .collect(Collectors.toList());
        log.info("Fetched all types");
        return typeResponses; 
    }

    private TypeResponse convertToTypeResponse(Type type) {
        return TypeResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .build();
    }
    
}
