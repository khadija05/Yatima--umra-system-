package com.yatima.config;
import com.yatima.security.JwtUtil; import com.yatima.repository.UserRepository;
import org.springframework.context.annotation.*; import org.springframework.security.authentication.*; import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.core.userdetails.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*; import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.io.IOException; import java.util.List;
@Configuration public class SecurityConfig {
  @Bean PasswordEncoder encoder(){ return new BCryptPasswordEncoder(); }
  @Bean UserDetailsService uds(UserRepository r){ return username-> r.findByUsername(username).map(u-> org.springframework.security.core.userdetails.User.builder().username(u.username).password(u.password).roles(u.role.replace("ROLE_","")).build()).orElseThrow(()-> new UsernameNotFoundException("Not found")); }
  @Bean AuthenticationManager am(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration c) throws Exception{ return c.getAuthenticationManager(); }
  @Bean CorsConfigurationSource cors(){ CorsConfiguration cfg=new CorsConfiguration(); cfg.setAllowedOriginPatterns(List.of("*")); cfg.setAllowedMethods(List.of("*")); cfg.setAllowedHeaders(List.of("*")); cfg.setAllowCredentials(true); UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource(); s.registerCorsConfiguration("/**",cfg); return s; }
  @Bean SecurityFilterChain chain(HttpSecurity http, JwtUtil jwt, UserDetailsService uds) throws Exception{
    OncePerRequestFilter filter=new OncePerRequestFilter(){
      protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain ch) throws IOException,ServletException{
        String h=req.getHeader("Authorization");
        if(h!=null && h.startsWith("Bearer ")){ String t=h.substring(7); if(jwt.valid(t)){ String un=jwt.extract(t); var ud=uds.loadUserByUsername(un); var auth=new UsernamePasswordAuthenticationToken(ud,null,ud.getAuthorities()); SecurityContextHolder.getContext().setAuthentication(auth); } }
        ch.doFilter(req,res);
      }
    };
    http.csrf(c->c.disable()).cors(c->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/plans","/api/tasisi","/api/tasisi/ranking/**","/api/umra","/api/packages","/h2-console/**").permitAll().anyRequest().authenticated())
    .headers(h->h.frameOptions(f->f.disable())).addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
