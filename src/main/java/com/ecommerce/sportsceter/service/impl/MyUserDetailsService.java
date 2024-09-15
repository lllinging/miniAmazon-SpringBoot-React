package com.ecommerce.sportsceter.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.ecommerce.sportsceter.respository.UserRepository;
import com.ecommerce.sportsceter.entity.User;
import com.ecommerce.sportsceter.model.LoginUser;


@Service
public class MyUserDetailsService implements UserDetailsService {    
    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("Username does not exist");
        }
        LoginUser loginUser = new LoginUser(user);
        return loginUser;
    }

    
}
