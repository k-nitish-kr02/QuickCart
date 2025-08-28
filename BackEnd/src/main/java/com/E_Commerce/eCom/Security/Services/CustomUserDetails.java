package com.E_Commerce.eCom.Security.Services;

import com.E_Commerce.eCom.Model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


@Data
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String email;


    private Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();

//        if(getUser.getRoles().isEmpty()) this.authorities = new ArrayList<>();

        this.authorities  = user.getRoles()
                .stream()
                .map(roles ->new SimpleGrantedAuthority(roles.getRoleName().toString()))
                .collect(Collectors.toList());


    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
