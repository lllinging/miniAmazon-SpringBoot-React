package com.ecommerce.sportsceter.service;

import org.springframework.http.ResponseEntity;

import com.ecommerce.sportsceter.model.JwtRequest;
import com.ecommerce.sportsceter.model.JwtResponse;
import com.ecommerce.sportsceter.model.UserDto;

public interface UserService {
    String addUser(UserDto userDto);

    ResponseEntity<JwtResponse> loginUser(JwtRequest loginDto);
}
