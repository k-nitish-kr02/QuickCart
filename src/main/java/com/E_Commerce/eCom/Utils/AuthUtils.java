package com.E_Commerce.eCom.Utils;

import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtils {

    private UserRepo userRepo;

    @Autowired
    public AuthUtils(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String loggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }


    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(authentication.getName());
    }
}
