package com.ecommerce.sportsceter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.sportsceter.model.JwtRequest;
import com.ecommerce.sportsceter.model.JwtResponse;
import com.ecommerce.sportsceter.model.UserDto;
import com.ecommerce.sportsceter.security.JwtHelper;

import com.ecommerce.sportsceter.service.UserService;
import com.ecommerce.sportsceter.service.impl.UserServiceImpl;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@Log4j2
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

    public AuthController(UserDetailsService userDetailsService, AuthenticationManager manager, JwtHelper jwtHelper, UserService userService, UserServiceImpl userServiceImpl) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.manager = manager;
        this.jwtHelper = jwtHelper;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public String saveUser (@RequestBody UserDto userDto){
        String id = userService.addUser(userDto);
        return id;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        return userServiceImpl.loginUser(request);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserDetails(@RequestHeader("Authorization") String tokenHeader){
        String token = extractTokenFromHeader(tokenHeader);
        if(token!=null){
            String username = jwtHelper.getUserNameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String extractTokenFromHeader(String tokenHeader) {
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer ")){
            return tokenHeader.substring(7); // Removing Bearer
        }
        return null;
    }


}
