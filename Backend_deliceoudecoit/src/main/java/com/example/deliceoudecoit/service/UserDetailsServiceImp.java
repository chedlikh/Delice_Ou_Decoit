package com.example.deliceoudecoit.service;

import com.example.deliceoudecoit.dao.TokenRepository;
import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private TokenRepository tokenRepository;

    private final UserRepository repository;

    public UserDetailsServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    public User updateUser(User user) {
        return repository.save(user);
    }
    public User getUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Transactional
    public void deleteUserByUsername(String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // First delete all related tokens
        tokenRepository.deleteAll(user.getTokens());

        // Then delete the user
        repository.delete(user);
    }
    private final String uploadDir = "src/images/image_profile/";

    public User uploadProfileImage(String username, MultipartFile file) throws IOException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create the directory if it doesn't exist
        Files.createDirectories(Paths.get(uploadDir));

        // Generate the file path
        String fileName = username + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Save the file to the local storage
        Files.write(filePath, file.getBytes());

        // Update the user entity with the file path
        user.setProfileImage(filePath.toString());
        return repository.save(user);
    }
}
