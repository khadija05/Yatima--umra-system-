// CorsConfig.java
package com.yatima.config; import org.springframework.context.annotation.*; import org.springframework.web.servlet.config.annotation.*;
@Configuration public class CorsConfig implements WebMvcConfigurer { @Override public void addCorsMappings(CorsRegistry r){ r.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowedHeaders("*").allowCredentials(false); } }
