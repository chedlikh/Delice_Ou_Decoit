package com.example.deliceoudecoit.service;

import com.example.deliceoudecoit.dao.CategoryRepo;
import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.example.deliceoudecoit.dao.ImageRepo;
import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.Category;
import com.example.deliceoudecoit.entities.Establishment;
import com.example.deliceoudecoit.entities.Image;
import com.example.deliceoudecoit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class EstablishmentService {
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ImageRepo imageRepo;

    public List<Establishment> getAllEstablishment(){return establishmentRepo.findAll();}

    public Establishment createEstablishment(Establishment establishment, MultipartFile file) {
        if (establishment == null) {
            throw new RuntimeException("Establishment cannot be null");
        }

        Long categoryId = establishment.getCategory().getId();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Establishment establishment1 = new Establishment();
        establishment1.setName(establishment.getName());
        establishment1.setDescription(establishment.getDescription());
        establishment1.setCategory(category);
        establishment1.setUser(establishment.getUser());

        String uniqueNameId = generateUniqueNameId(establishment1.getName());
        establishment1.setNameId(uniqueNameId);

        // Save establishment first to get its ID
        Establishment savedEstablishment = establishmentRepo.save(establishment1);

        // Handle file upload if present
        if (file != null && !file.isEmpty()) {
            try {
                String imageUrl = storeImage(savedEstablishment.getId(), file); // Save the image and get URL
                Image image = new Image();
                image.setUrl(imageUrl);
                image.setEstablishment(savedEstablishment);

                List<Image> images = new ArrayList<>();
                images.add(image);
                savedEstablishment.setImages(images);

                // Save the establishment again with the associated images
                savedEstablishment = establishmentRepo.save(savedEstablishment);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store image", e);
            }
        }

        System.out.println("Establishment details: " + savedEstablishment);
        return savedEstablishment;
    }


    public Establishment updateEstablishment(String nameId, Establishment UEstablishment) {
        Establishment establishment = establishmentRepo.findByNameId(nameId)
                .orElseThrow(() -> new RuntimeException("Establishment not found with name: " + nameId));

        establishment.setName(UEstablishment.getName());
        establishment.setDescription(UEstablishment.getDescription());
        String uniqueNameId = generateUniqueNameId(establishment.getName());
        establishment.setNameId(uniqueNameId);

        return establishmentRepo.save(establishment);
    }

    private String generateUniqueNameId(String name) {
        if (name == null) {
            return null;
        }

        // Replace spaces and periods with underscores
        String baseNameId = name.replace(" ", "_").replace(".", "_");
        String nameId = baseNameId;
        int counter = 1;

        // Log base nameId
        System.out.println("Base nameId: " + baseNameId);

        // Check if the baseNameId already exists
        while (establishmentRepo.existsByNameId(nameId)) {
            System.out.println("NameId already exists: " + nameId);
            nameId = baseNameId + "_" + counter;
            counter++;
            System.out.println("Trying new nameId: " + nameId);
        }

        return nameId;
    }

    public void deleteEstablishment(String nameId) {
        Establishment establishment = establishmentRepo.findByNameId(nameId)
                .orElseThrow(() -> new RuntimeException("Establishment not found with name: " + nameId));
        establishmentRepo.delete(establishment);
    }
    @Value("${image.upload.dir}")
    private String uploadDir;

    public String storeImage(Long establishmentId, MultipartFile file) throws IOException {
        // Generate a unique file name
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Define the path where the file will be saved
        Path filePath = Paths.get(uploadDir, uniqueFileName);

        // Save the file to the specified path
        Files.copy(file.getInputStream(), filePath);

        // Return the URL or path where the image is stored
        return uniqueFileName; // or a full URL if required
    }

    public Establishment addImageToEstablishment(Long establishmentId, String imageUrl) {
        Establishment establishment = establishmentRepo.findById(establishmentId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        Image image = new Image();
        image.setUrl(imageUrl);
        image.setEstablishment(establishment);

        establishment.getImages().add(image);
        establishmentRepo.save(establishment);

        return establishment;
    }
    public Image getImageByEstablishmentAndImageId(Long establishmentId, Long imageId) {
        // Vérifie si l'établissement existe
        Establishment establishment = establishmentRepo.findById(establishmentId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        // Rechercher l'image par son ID parmi les images de l'établissement
        return establishment.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found for this establishment"));
    }
    public Establishment uploadcardImage(String nameId, MultipartFile file) throws IOException {
        Establishment establishment = establishmentRepo.findByNameId(nameId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create the directory if it doesn't exist
        Files.createDirectories(Paths.get(uploadDir));

        // Generate the file path
        String fileName = nameId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        // Save the file to the local storage
        Files.write(filePath, file.getBytes());

        // Update the user entity with the file path
        establishment.setCardImage(filePath.toString());
        return establishmentRepo.save(establishment);
    }


    public void deleteImage(Long establishmentId, Long imageId) {
        Establishment establishment = establishmentRepo.findById(establishmentId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        Image image = imageRepo.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Remove image from the establishment
        establishment.getImages().remove(image);

        // Optionally delete the image file from the file system or cloud storage
        deleteImageFile(image.getUrl());

        // Save changes
        establishmentRepo.save(establishment);

        // Delete the image record from the database
        imageRepo.delete(image);
    }

    private void deleteImageFile(String imageUrl) {
        // Implement file deletion logic based on where images are stored
        // For example, if you are using a local file system:
        File file = new File(imageUrl);
        if (file.exists()) {
            file.delete();
        }
    }
    public List<Image> getImagesByEstablishment(Long establishmentId) {
        List<Image> images = imageRepo.findByEstablishmentId(establishmentId);
        return images;
    }
}
