package com.yatima.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String secret;

  private Key key(){ return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); }

  // HII NDIO SAHIHI - args 3: username, role, tenantId
  public String generate(String username, String role, String tenantId){
    return Jwts.builder()
      .setSubject(username)
      .claim("role", role)
      .claim("tenantId", tenantId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis()+1000L*60*60*24*30))
      .signWith(key(), SignatureAlgorithm.HS256)
      .compact();
  }

  public Claims parse(String auth){
    if(auth==null) throw new RuntimeException("No token");
    String t = auth.startsWith("Bearer ") ? auth.substring(7) : auth;
    return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(t).getBody();
  }
}
