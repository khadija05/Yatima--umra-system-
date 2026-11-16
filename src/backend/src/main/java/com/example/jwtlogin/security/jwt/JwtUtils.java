package com.example.jwtlogin.security.jwt;
import io.jsonwebtoken.*; import io.jsonwebtoken.io.Decoders; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import org.springframework.security.core.Authentication;
import java.security.Key; import java.util.Date;
@Component
public class JwtUtils {
  @Value("${jwt.secret}") private String jwtSecret;
  @Value("${jwt.expirationMs}") private int jwtExpirationMs;
  private Key key(){ return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)); }
  public String generateJwtToken(Authentication auth){ return Jwts.builder().setSubject(auth.getName()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+jwtExpirationMs)).signWith(key(), SignatureAlgorithm.HS512).compact(); }
  public String getUserNameFromJwtToken(String token){ return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject(); }
  public boolean validateJwtToken(String token){ try{ Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token); return true; } catch(Exception e){ return false; } }
}
