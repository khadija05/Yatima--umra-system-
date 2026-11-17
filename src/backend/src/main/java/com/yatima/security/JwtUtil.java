package com.yatima.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;
@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String secret;
  private SecretKey key() { return Keys.hmacShaKeyFor(secret.getBytes()); }
  public String generate(String username, String role) {
    return Jwts.builder().setSubject(username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 7L*24*60*60*1000)).signWith(key()).compact();
  }
  public Claims parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token.replace("Bearer ","")).getBody();
  }
}
