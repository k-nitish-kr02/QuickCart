package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Model.AppRole;
import com.E_Commerce.eCom.Model.Role;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Repository.RoleRepository;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Security.Services.CustomUserDetails;
import com.E_Commerce.eCom.Security.Services.JwtService;
import com.E_Commerce.eCom.Security.Services.UserDetailsServiceImpl;
import com.E_Commerce.eCom.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AuthController {


    private final JwtService jwtService;

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(JwtService jwtService, AuthService authService, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }


    @PostMapping("/public/signUp")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody SignUpRequest request){

        AuthResponse response = authService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest authRequest){

        return ResponseEntity.ok(authService.signIn(authRequest));

    }

    @GetMapping("/auth/health-check")
    public String healthcheck(){
        return "everything is working fine.";
    }

    @PostMapping("/auth/role")
    public ResponseEntity<?> createRole(@RequestBody  String role){
        AppRole appRole = switch (role) {
            case "admin" -> AppRole.ROLE_ADMIN;
            case "seller" -> AppRole.ROLE_SELLER;
            default -> AppRole.ROLE_USER;
        };
        Role savedRole = Optional.ofNullable(roleRepository.findByRoleName(appRole)).orElseGet(
                ()-> roleRepository.save(new Role(appRole))
        );
        return ResponseEntity.ok(savedRole);
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
