package com.personal.recipebackend.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${secretKey}")
    private String SECRET_KEY;

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();
    }

    public String extractUserName(String token){
        return extractAllClaim(token).getSubject();
    }

    public Date extractExpiration(String token){
        return extractAllClaim(token).getExpiration();
    }

    private Claims extractAllClaim(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractClaim(String token, String claim){
        return extractAllClaim(token).get(claim, String.class);
    }

    public Boolean validateToken(String token, String userName){
        final String extractedUsername = extractUserName(token);
        return (extractedUsername.equals(userName) && !isTokenExpired(token));
    }
}
