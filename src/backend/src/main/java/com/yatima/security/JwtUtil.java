package com.yatima.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component;
import java.security.Key; import java.util.Date;
@Component public class JwtUtil {
  @Value("${jwt.secret}") private String secret;
  @Value("${jwt.expiration}") private long exp;
  private Key key(){ return Keys.hmacShaKeyFor(secret.getBytes()); }
  public String generate(String username){ return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+exp)).signWith(key(),SignatureAlgorithm.HS256).compact(); }
  public String extract(String token){ return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject(); }
  public boolean valid(String token){ try{ Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token); return true;}catch(Exception e){return false;} }
}
