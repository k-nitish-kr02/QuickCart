package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.Model.AppRole;
import com.E_Commerce.eCom.Model.Role;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Payload.RolePayload.RoleDTO;
import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import com.E_Commerce.eCom.Repository.RoleRepository;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Security.Services.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<String> requestedRoles =request.getRoles();
        Set<Role> roles = new HashSet<>();


        if(requestedRoles.isEmpty()){
            Role usrRole = Optional.ofNullable(roleRepository.findByRoleName(AppRole.ROLE_USER)).orElseThrow(()-> new APIException("No such role found"));
            roles.add(usrRole);
        }
        else{
            requestedRoles.forEach(role -> {
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

        Set<String> rolesDTO = savedUser.getRoles().stream().map(role -> role.getRoleName().toString()).collect(Collectors.toSet());

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoles(rolesDTO);

        savedUserDTO.setRoleGranted(roleDTO);


//        String token = jwtService.createJwt(request.getUsername());
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(request.getUsername());
        String token = jwtCookie.toString();

        return new AuthResponse(jwtCookie, savedUserDTO);
    }

    public AuthResponse signIn(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

//        String token = jwtService.createJwt(username);
        ResponseCookie cookie = jwtService.generateJwtCookie(username);
        User user  = userRepo.findByUsername(username);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        Set<String> rolesDTO = user.getRoles().stream().map(role -> role.getRoleName().toString()).collect(Collectors.toSet());

        userDTO.setRoleGranted( new RoleDTO(rolesDTO) );
        return new AuthResponse(cookie,userDTO);
    }
}
