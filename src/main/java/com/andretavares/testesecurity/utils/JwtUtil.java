package com.andretavares.testesecurity.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    // public static final String SECRET = "csezCexuHSZGNw/lQsvdASWH5nUTprjM0CbZiwImlcOlGBnUvJt3ak5LQ6N456nhHZraln5BXdYDEdI4HK7yteWCPDHiTQUO6ao7xRfFtK1JlK5zD3Qb4axTArpZAey+tYNkg616GexAjymQOm2t4PBpog+OdMLo2J7yBxTB0D+n4XXUTgR9atecSmbFG8VBUOQefCm5e4qePZ/Axy8UL8YQU9Bc268vatFjR9z+XbCQae2SoS2+wCcvjz/Fo56TSfk1P0X26Ox+KdV2hI3BmfxUuZ5EX1SdK2vPizRAVKH2sRsV4RfrVs3/leS3SiOngmYDDu7OMxJIMynkXyodDdOUsRnyZRsPCrJ2r/HOyMw=";

    public String extractUsername(String token){return extractClaim(token,Claims::getSubject);}

    public String extractId(String token){
        return extractClaim(token, Claims::getId);
    }

    public Date extractExpiration(String token){return extractClaim(token,Claims::getExpiration);}

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username,String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,username);
    }

    private String createToken(Map<String, Object> claims, String username){
        return Jwts 
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
