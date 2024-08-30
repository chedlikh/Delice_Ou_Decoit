package com.example.deliceoudecoit.configuration;

import com.example.deliceoudecoit.dao.TokenRepository;
import com.example.deliceoudecoit.entities.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);  // Define the logger


    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // Extract the JWT token and log it for debugging
        String token = authHeader.substring(7);
        log.debug("Received JWT Token during logout: {}", token);

        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);

        if (storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
