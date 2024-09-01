package io.kennen.shortlink.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
    @Override  
    public void addCorsMappings(CorsRegistry registry) {
        // 允许来自任何源的请求  
        // 注意：在生产环境中，你应该只允许特定的源  
        registry.addMapping("/**")  
            .allowedOrigins("http://118.178.231.120") // 或者 "*" 来允许所有源  
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")  
            .allowCredentials(true)  
            .maxAge(3600)  
            .allowedHeaders("*");  
    }  
}