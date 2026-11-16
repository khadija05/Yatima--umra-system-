package com.example.jwtlogin.security.jwt;

import com.example.jwtlogin.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  JwtUtils jwt;

  @Autowired
  UserDetailsServiceImpl svc;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain ch)
      throws ServletException, IOException {

    String h = req.getHeader("Authorization");

    if (h != null && h.startsWith("Bearer ")) {
      String t = h.substring(7);
      if (jwt.validate(t)) {
        String u = jwt.getUser(t);
        UserDetails d = svc.loadUserByUsername(u);
        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(d, null, d.getAuthorities());
        a.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(a);
      }
    }
    ch.doFilter(req, res);
  }
}
