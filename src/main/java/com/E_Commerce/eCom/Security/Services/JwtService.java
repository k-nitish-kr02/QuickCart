package com.E_Commerce.eCom.Security.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.sql.Time;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.secretKey}")
    private String SECRET_KEY ;


    private SecretKey getSignedKey(){
        byte [] key = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // parsing of jwt
    private <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // username extraction
    public String extractUsername(String token) {
        return  extractClaim(token,claims -> claims.getSubject());
    }

    // validation -> isExpired
    public boolean isTokenValid(String token){
        return !extractClaim(token,claims -> claims.getExpiration()).before(new Date(System.currentTimeMillis()));
    }


    // generation of token
    public String createJwt(String username){
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *5))
                .and()
                .signWith(getSignedKey())
                .compact();
    }

}
