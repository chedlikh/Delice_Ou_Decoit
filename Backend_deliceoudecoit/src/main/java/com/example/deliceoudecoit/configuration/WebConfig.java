package com.example.deliceoudecoit.configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsConfiguration configuration = new CorsConfiguration();
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Add your frontend URL here
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Handler for profile images
        registry.addResourceHandler("/images/profile/**")
                .addResourceLocations("file:/images/image_profile/");

        // Handler for establishment images
        registry.addResourceHandler("/images/establishment/**")
                .addResourceLocations("file:/images/image_establishment/"); // Change to relative path
    }
}
