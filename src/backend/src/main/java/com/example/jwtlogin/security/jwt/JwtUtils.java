package com.example.jwtlogin.security.jwt;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Component; import java.security.Key; import java.util.Date;
@Component public class JwtUtils {
  @Value("${jwt.secret}") private String secret; @Value("${jwt.expiration}") private int exp;
  private Key key(){ return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(secret)); }
  public String generate(String u){ return Jwts.builder().setSubject(u).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+exp)).signWith(key()).compact(); }
  public String getUser(String t){ return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(t).getBody().getSubject(); }
  public boolean validate(String t){ try{ Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(t); return true; }catch(Exception e){ return false; } }
}
