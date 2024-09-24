package com.example.deliceoudecoit.controller;

import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.AuthenticationResponse;
import com.example.deliceoudecoit.entities.User;
import com.example.deliceoudecoit.service.AuthenticationService;
import com.example.deliceoudecoit.service.UserDetailsServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class AuthenticationController {
    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    private final AuthenticationService authService;
    private final UserRepository repository;




    public AuthenticationController(AuthenticationService authService,UserRepository repository) {
        this.authService = authService;
        this.repository = repository;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticationResponse> getProfile() {
        // Retrieve the Authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Ensure `getPrincipal` returns `UserDetails`
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Retrieve the User entity based on the username
        User user = repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Create the response object with user profile details
        AuthenticationResponse profileResponse = new AuthenticationResponse(
                null, // No access token needed
                null, // No refresh token needed
                "User profile retrieved successfully",
                user.getUsername(),
                user.getFirstName(),
                user.getlastname(),
                user.getRole().name(), // Convert Role to String
                user.getDatenaissance(),
                user.getGender(),
                user.getNumberphone(),
                user.getCountry(),
                user.getState(),
                user.getProfileImage()
        );

        return ResponseEntity.ok(profileResponse);
    }
    @PutMapping("/me")
    public ResponseEntity<AuthenticationResponse> updateProfile(@RequestBody AuthenticationResponse updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Ensure `getPrincipal` returns `UserDetails`
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Retrieve the User entity based on the username
        User user = repository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Update user profile
        user.setFirstName(updateRequest.getFirstname());
        user.setlastname(updateRequest.getlastname());
        user.setDatenaissance(updateRequest.getDatenaissance());
        user.setGender(updateRequest.getGender());
        user.setCountry(updateRequest.getCountry());
        user.setState(updateRequest.getState());

        User updatedUser = userDetailsServiceImp.updateUser(user);

        // Create response object
        AuthenticationResponse profileResponse = new AuthenticationResponse(
                null, // No access token needed
                null, // No refresh token needed
                "User profile updated successfully",
                updatedUser.getUsername(),
                updatedUser.getFirstName(),
                updatedUser.getlastname(),
                updatedUser.getRole().toString(),
                updatedUser.getDatenaissance(),
                updatedUser.getGender(),
                updatedUser.getNumberphone(),
                updatedUser.getCountry(),
                updatedUser.getState()
        );

        return ResponseEntity.ok(profileResponse);
    }
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userDetailsServiceImp.deleteUserByUsername(userDetails.getUsername());

        return ResponseEntity.ok("User profile deleted successfully");
    }
    @PostMapping("/uploadProfileImage")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("username") String username, @RequestParam("file") MultipartFile file) {
        try {
            User user = userDetailsServiceImp.uploadProfileImage(username, file);
            return ResponseEntity.ok("Profile image uploaded successfully. Image path: " + user.getProfileImage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }
    private final String uploadDir = "src/images/image_profile/";
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();

            if (Files.exists(filePath)) {
                Resource resource = new UrlResource(filePath.toUri());

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
