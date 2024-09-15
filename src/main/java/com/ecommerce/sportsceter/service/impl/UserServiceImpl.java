package com.ecommerce.sportsceter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.sportsceter.entity.User;
import com.ecommerce.sportsceter.model.JwtRequest;
import com.ecommerce.sportsceter.model.JwtResponse;
import com.ecommerce.sportsceter.model.LoginUser;
import com.ecommerce.sportsceter.model.UserDto;
import com.ecommerce.sportsceter.respository.UserRepository;
import com.ecommerce.sportsceter.security.JwtHelper;
import com.ecommerce.sportsceter.service.UserService;


import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public String addUser(UserDto userDto) {
        User user = new User(
                userDto.getUserId(),
                userDto.getUsername(),
                userDto.getEmail(),
                this.passwordEncoder.encode(userDto.getPassword()));

        userRepo.save(user);
        return user.getUsername();
    }

    @Override
    public ResponseEntity<JwtResponse> loginUser(JwtRequest loginDto) {
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(), loginDto.getPassword());
                    System.out.println("authenticationToken"+authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
            System.out.println("authentication object:"+ authentication);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // wrong password, return 401
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        System.out.println("principal"+principal);
        String username = principal.getUser().getUsername().toString();
        System.out.println("username"+username);
        String token = this.jwtHelper.generateToken(username);
                JwtResponse response = JwtResponse.builder()
                .username(loginDto.getUsername())
                .token(token)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

        
    }
}
