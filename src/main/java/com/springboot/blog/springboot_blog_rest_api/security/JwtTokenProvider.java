package com.springboot.blog.springboot_blog_rest_api.security;

import com.springboot.blog.springboot_blog_rest_api.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    // get username for JWT token
    public String getUsername(String token){
      return Jwts.parserBuilder()
              .setSigningKey((SecretKey) key())
              .build()
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    }

    // validate JWT Token
    public boolean validationToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey((SecretKey) key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid Token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid Expired Token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid Unsupportd Token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid empty Token");
        }
    }
}
