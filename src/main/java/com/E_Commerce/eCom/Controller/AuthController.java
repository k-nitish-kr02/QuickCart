package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Security.Services.CustomUserDetails;
import com.E_Commerce.eCom.Security.Services.JwtService;
import com.E_Commerce.eCom.Security.Services.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {


    private final JwtService jwtService;

    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtService jwtService, UserRepo userRepo, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }


//    private UserService userService;

    @PostMapping("/public/signUp")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
//            User user = userService.createUser();
        userRepo.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest){
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        String token = jwtService.createJwt(username);
        return new ResponseEntity<>(token,HttpStatus.OK);

    }

    public ResponseEntity<?> getAllUsers(){
        return null;
    }

    public ResponseEntity<?> getUser(){
        return null;
    }

    public ResponseEntity<?> deleteUser(){
        return null;
    }

    public ResponseEntity<?> updateUser(){
        return null;
    }

}
