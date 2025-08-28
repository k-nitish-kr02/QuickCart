package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Configurations.AppConstants;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import com.E_Commerce.eCom.Payload.UserPayload.UserResponse;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/auth/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){
        UserDTO user = authService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/auth/signOut")
    public ResponseEntity<?> signOut(){
        ResponseCookie cookie = authService.cleanCurrentCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body("Signed out successfully..");
    }

    @DeleteMapping("/auth/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        UserDTO userDTO = authService.deleteUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/auth/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO){
        UserDTO user = authService.updateUser(userId,userDTO);
        return ResponseEntity.ok(user);
    }

}
