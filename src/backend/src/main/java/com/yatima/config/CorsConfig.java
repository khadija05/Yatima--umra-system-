package com.yatima.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override public void addCorsMappings(CorsRegistry r) {
    r.addMapping("/**")
      .allowedOriginPatterns("*")
      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS","HEAD")
      .allowedHeaders("*")
      .exposedHeaders("Authorization", "Content-Type", "X-Total-Count")
      .allowCredentials(true)
      .maxAge(3600);
  }

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOriginPatterns(List.of(
      "*",
      "https://*.onrender.com",
      "https://*.netlify.app",
      "https://*.vercel.app",
      "http://localhost:*",
      "http://127.0.0.1:*"
    ));
    config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS","HEAD"));
    config.setAllowedHeaders(List.of("*"));
    config.setExposedHeaders(List.of("Authorization", "Content-Type", "X-Total-Count"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
