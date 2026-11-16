package com.example.jwtlogin.security;
import com.example.jwtlogin.security.jwt.*;
import org.springframework.beans.factory.annotation.Autowired; import org.springframework.context.annotation.*; import org.springframework.security.authentication.AuthenticationManager; import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class WebSecurityConfig {
  @Autowired private AuthEntryPointJwt unauthorizedHandler;
  @Bean public AuthTokenFilter authenticationJwtTokenFilter(){ return new AuthTokenFilter(); }
  @Bean public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
  @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration c) throws Exception { return c.getAuthenticationManager(); }
  @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable()).exceptionHandling(e->e.authenticationEntryPoint(unauthorizedHandler)).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**","/h2-console/**","/api/tasisi","/api/umra","/api/packages","/api/plans/**").permitAll().anyRequest().authenticated());
    http.headers(h->h.frameOptions(f->f.disable()));
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
