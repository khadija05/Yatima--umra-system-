package com.yatima.config;
import com.yatima.util.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  public SecurityConfig(JwtAuthFilter jwtAuthFilter) { this.jwtAuthFilter = jwtAuthFilter; }

  @Bean public PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

  @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean public SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http
      .cors(cors -> {})
      .csrf(c -> c.disable())
      .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(a -> a
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/packages/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/orphans/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/umra/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/tasisi/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/donations").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/bookings").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/payments").permitAll()
        .requestMatchers("/", "/index.html", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.jpg", "/favicon.ico").permitAll()
        .requestMatchers("/actuator/health", "/error").permitAll()
        .requestMatchers("/api/admin/**").authenticated()
        .anyRequest().authenticated()
      )
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
