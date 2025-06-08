package com.E_Commerce.eCom.Security.Filters;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.Security.Configurations.AuthEntryPointJwt;
import com.E_Commerce.eCom.Security.Services.JwtService;
import com.E_Commerce.eCom.Security.Services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;


    @Autowired
    public JwtFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        String authHeader = request.getHeader("Authorization");
//        String token =null;
//        String  username=null;
//        if(authHeader!=null && authHeader.startsWith("Bearer ")){
//            token = authHeader.substring(7);
//        }

        String token = jwtService.getJwtFromCookie(request);
        String username = null;


        if(jwtService.isTokenValid(token))
            username = Optional.ofNullable(jwtService.extractUsername(token)).orElseThrow(() -> new APIException("Invalid token")) ;

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

        }


        filterChain.doFilter(request,response);


    }
}
