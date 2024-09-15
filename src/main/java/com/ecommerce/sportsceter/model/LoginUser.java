package com.ecommerce.sportsceter.model;

import java.io.Serial;
import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.ecommerce.sportsceter.entity.User;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails, Serializable{
    @Serial
    private static final long serialVersionUID = 4012278606316008413L;

    private User user;  // save the user object from the database 

  	// Returns the authorities granted to the user.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
    @Override
    public String getPassword() {  // get the password of the user
        return user.getPassword();
    }
    @Override
    public String getUsername() {  // get the username of the user
        return user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {   // check if the account is not expired
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {   // check if the account is not locked
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {  // check if the credentials are not expired
        return true;
    }
    

}
