
package com.yatima.util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtUtil {
private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
private final long EXP = 1000L*60*60*24*7;
public Claims parse(String auth){
String t = auth == null ? "" : auth.replace("Bearer ","").trim();
return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(t).getBody();
}
public String generateToken(String username, String role, String tenantId){
Map<String,Object> c = new HashMap<>();
c.put("role", role);
c.put("tenantId", tenantId);
return Jwts.builder().setClaims(c).setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+EXP)).signWith(key).compact();
}
public String generateToken(String username){ return generateToken(username,"USER","default"); }
public String extractUsername(String t){ return parse(t).getSubject(); }
public String getUsernameFromToken(String t){ return extractUsername(t); }
public String getRole(String t){ Object v = parse(t).get("role"); return v==null?null:v.toString(); }
public String getTenantId(String t){ Object v = parse(t).get("tenantId"); return v==null?null:v.toString(); }
public boolean validateToken(String t){ try{ parse(t); return true; }catch(Exception e){ return false; } }
}
