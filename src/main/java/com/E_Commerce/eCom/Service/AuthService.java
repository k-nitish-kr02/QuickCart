package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.Model.AppRole;
import com.E_Commerce.eCom.Model.Role;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import com.E_Commerce.eCom.Repository.RoleRepository;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Security.Services.JwtService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXAuthenticator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepo userRepo, RoleRepository roleRepository, JwtService jwtService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    //SignUp handler
    public AuthResponse save(SignUpRequest request) {
        if(userRepo.existsByUsername(request.getUsername())){
            throw new APIException("username already exists..");
        }

        if(userRepo.existsByEmail(request.getEmail())){
            throw new APIException("Email already exists..");
        }

        User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .build();

        Set<String> strRole =request.getRoles();
        Set<Role> roles = new HashSet<>();


        if(strRole.isEmpty()){
            Role usrRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_USER)).orElseThrow(()-> new APIException("No such role found"));
            roles.add(usrRole);
        }
        else{
            strRole.forEach(role -> {
                    switch(role){
                        case "admin" :
                            Role adminRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_ADMIN)).orElseThrow(()-> new APIException("No such role found"));
                            roles.add(adminRole);
                            break;
                        case "seller":
                            Role sellerRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_SELLER)).orElseThrow(()-> new APIException("No such role found"));
                            roles.add(sellerRole);

                            break;
                        default:
                            Role usrRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_USER)).orElseThrow(()-> new APIException("No such role found"));
                            roles.add(usrRole);
                            break;
                    }
            });
        }

        user.setRoles(roles);
        User savedUser = userRepo.save(user);
        UserDTO savedUserDTO = modelMapper.map(savedUser,UserDTO.class);

        String token = jwtService.createJwt(request.getUsername());

        return new AuthResponse(token, savedUserDTO);
    }

    public AuthResponse signIn(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        String token = jwtService.createJwt(username);
        User user  = userRepo.findByUsername(username);
        return new AuthResponse(token,modelMapper.map(user, UserDTO.class));
    }
}
