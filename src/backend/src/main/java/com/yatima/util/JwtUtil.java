package com.yatima.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;
@Component public class JwtUtil{
 @Value("${jwt.secret}") private String secret;
 private java.security.Key key(){ return Keys.hmacShaKeyFor(secret.getBytes()); }
 public String generate(String username,String role,String tenantId){
  return Jwts.builder().setSubject(username).claim("role",role).claim("tenantId",tenantId).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+86400000*30)).signWith(key()).compact();
 }
 public Claims parse(String auth){
  if(auth==null) throw new RuntimeException("No token");
  String t=auth.replace("Bearer ","");
  return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(t).getBody();
 }
}
