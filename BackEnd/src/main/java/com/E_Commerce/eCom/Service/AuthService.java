package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Model.AppRole;
import com.E_Commerce.eCom.Model.Role;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.Responses.AuthResponse;
import com.E_Commerce.eCom.Payload.RolePayload.RoleDTO;
import com.E_Commerce.eCom.Payload.UserPayload.UserDTO;
import com.E_Commerce.eCom.Payload.UserPayload.UserResponse;
import com.E_Commerce.eCom.Repository.RoleRepository;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Requests.AuthRequest;
import com.E_Commerce.eCom.Requests.SignUpRequest;
import com.E_Commerce.eCom.Security.Services.JwtService;
import com.E_Commerce.eCom.Utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
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

    @Value("${spring.jwt.cookie}")
    private String jwtCookie;


    @Autowired
    public AuthService(UserRepo userRepo, RoleRepository roleRepository, JwtService jwtService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    private UserDTO generateUserDTO(User user){

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        RoleDTO roleDTO = new RoleDTO(
                user.getRoles().stream()
                        .map(role -> role.getRoleName().toString())
                        .collect(Collectors.toSet())
        );
        userDTO.setRoleGranted(roleDTO);
        return userDTO;
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

        return new AuthResponse(jwtCookie, savedUserDTO);
    }

    public AuthResponse signIn(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();


        authenticationManager.authenticate(
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


    public ResponseCookie cleanCurrentCookie() {

        return ResponseCookie.from(jwtCookie,null).path("/api").build();
    }

    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userRepo.findAll(pageDetails);
        List<User> users = page.getContent();

        List<UserDTO> userDTOs = users.stream().map(user -> {
            Set<String> roles = new HashSet<>();
            roles = user.getRoles().stream().map(role -> role.getRoleName().toString()).collect(Collectors.toSet());
            RoleDTO roleDTO = new RoleDTO(roles);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setRoleGranted(roleDTO);
            return userDTO;
        }).toList();

        return UserResponse.builder()
                .users(userDTOs)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .lastPage(page.isLast())
                .build();
    }

    public UserDTO deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));

        UserDTO userDTO = generateUserDTO(user);

        userRepo.delete(user);
        return userDTO;
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));

        return generateUserDTO(user);
    }

    public UserDTO updateUser(Long userId,UserDTO userDTO) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        ObjectUtils.setIfNotNull(userDTO.getEmail(),user::setEmail);
        ObjectUtils.setIfNotNull(userDTO.getUsername(), user::setUsername);

        return generateUserDTO(user);
    }
}
