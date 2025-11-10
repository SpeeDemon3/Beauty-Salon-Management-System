package com.ruiz.Beauty.Salon.Management.System.service;

import com.ruiz.Beauty.Salon.Management.System.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para manejar operaciones con JWT.
 */
@Service
@Slf4j
public class JwtService {
    private final String secretKey;
    private final long jwtExpiration;

    public JwtService(@Value("${jwt.secret-key}") String secretKey,
                      @Value("${jwt.expiration}") long jwtExpiration) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.jwtExpiration = jwtExpiration;
    }

    /**
     * Genera un token JWT para un empleado.
     */
    public String generateToken(Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", employee.getRol());
        claims.put("name", employee.getName());
        claims.put("id", employee.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(employee.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el email del token JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public LocalDateTime extractExpiration(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Valida un token JWT.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(LocalDateTime.now());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
