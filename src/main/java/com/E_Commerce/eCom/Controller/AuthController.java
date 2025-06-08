package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Configurations.AppConstants;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import com.E_Commerce.eCom.Payload.UserPayload.UserResponse;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthController {


    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/public/signUp")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody SignUpRequest request){

        AuthResponse response = authService.save(request);
        ResponseCookie jwtCookie = response.getJwtCookie();
        UserDTO userInfoResponse = response.getUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString()).body(userInfoResponse);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest authRequest){
        AuthResponse response = authService.signIn(authRequest);
        ResponseCookie jwtCookie = response.getJwtCookie();
        UserDTO userInfoResponse = response.getUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString()).body(userInfoResponse);
    }

    @GetMapping("/health-check")
    public String healthcheck(){
        return "everything is working fine.";
    }

//    @PostMapping("/auth/role")
//    public ResponseEntity<?> createRole(@RequestBody  String role){
//        AppRole appRole = switch (role) {
//            case "admin" -> AppRole.ROLE_ADMIN;
//            case "seller" -> AppRole.ROLE_SELLER;
//            default -> AppRole.ROLE_USER;
//        };
//        Role savedRole = Optional.ofNullable(roleRepository.findByRoleName(appRole)).orElseGet(
//                ()-> roleRepository.save(new Role(appRole))
//        );
//        return ResponseEntity.ok(savedRole);
//    }

    @GetMapping("/admin/auth/getAllUsers")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ){
        UserResponse response = authService.getAllUsers(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getUser(){
        return null;
    }
    @PostMapping("/auth/signOut")
    public ResponseEntity<?> signOut(){
        ResponseCookie cookie = authService.cleanCurrentCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body("Signed out successfully..");
    }

    public ResponseEntity<?> deleteUser(){
        return null;
    }

    public ResponseEntity<?> updateUser(){
        return null;
    }

}
