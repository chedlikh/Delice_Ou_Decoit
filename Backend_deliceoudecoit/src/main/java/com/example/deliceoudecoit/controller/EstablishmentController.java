package com.example.deliceoudecoit.controller;

import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.*;
import com.example.deliceoudecoit.service.EstablishmentService;
import com.example.deliceoudecoit.service.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/establishment")
public class EstablishmentController {
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(EstablishmentController.class);
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEstablishment(
            @RequestPart("establishment") String establishmentJson,
            @RequestPart("file") MultipartFile file) {

        try {
            // Convert JSON string to Establishment object
            ObjectMapper objectMapper = new ObjectMapper();
            Establishment establishment = objectMapper.readValue(establishmentJson, Establishment.class);

            Establishment createdEstablishment = establishmentService.createEstablishment(establishment, file);
            return ResponseEntity.ok(createdEstablishment);

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid JSON data: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Establishment>> getAllEstablishment(){
        List<Establishment> establishments=establishmentService.getAllEstablishment();
        return ResponseEntity.ok(establishments);
    }
    @GetMapping("/{nameId}")
    public ResponseEntity<Establishment> getEstablishmentByNameId(@PathVariable String nameId) {
        Establishment establishment = establishmentRepo.findByNameId(nameId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));
        return ResponseEntity.ok(establishment);
    }
    @PutMapping("/{nameId}")
    public ResponseEntity<Establishment> updateEstablishment(
            @PathVariable String nameId,
            @RequestBody Establishment UEstablishment) {

        // Validate the incoming establishment object if needed
        if (UEstablishment == null) {
            return ResponseEntity.badRequest().build();
        }

        Establishment updatedEstablishment = establishmentService.updateEstablishment(nameId, UEstablishment);
        return ResponseEntity.ok(updatedEstablishment);
    }


    @DeleteMapping("/{nameId}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable String nameId) {
        establishmentService.deleteEstablishment(nameId);
        return ResponseEntity.noContent().build();  // Return 204 No Content
    }
    @PostMapping("/{id}/images")
    public ResponseEntity<Establishment> addImageToEstablishment(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        // Handle the file upload
        String imageUrl = establishmentService.storeImage(id, file);

        // Return the updated establishment
        Establishment updatedEstablishment = establishmentService.addImageToEstablishment(id, imageUrl);
        return ResponseEntity.ok(updatedEstablishment);
    }
    @GetMapping("/{establishmentId}/images/{imageId}")
    public ResponseEntity<Image> getImageByEstablishmentAndImageId(
            @PathVariable Long establishmentId,
            @PathVariable Long imageId) {
        Image image = establishmentService.getImageByEstablishmentAndImageId(establishmentId, imageId);
        return ResponseEntity.ok(image);
    }
    @PostMapping("/uploadCardImage")
    public ResponseEntity<?> uploadCardImage(@RequestParam("nameId") String nameId, @RequestParam("file") MultipartFile file) {
        try {
            Establishment establishment = establishmentService.uploadcardImage(nameId, file);
            return ResponseEntity.ok("Card image uploaded successfully. Image path: " + establishment.getCardImage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        }
    }
    private final String uploadDir = "src/images/";
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
    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Path file = Paths.get("src/images/image_establishment/").resolve(filename);
        Resource resource;
        try {
            resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @DeleteMapping("/establishments/{establishmentId}/images/{imageId}")
    public ResponseEntity<String> deleteImage(
            @PathVariable Long establishmentId,
            @PathVariable Long imageId) {
        try {
            establishmentService.deleteImage(establishmentId, imageId);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete image");
        }
    }
    @PostMapping("/ratings/{establishmentId}")
    public ResponseEntity<Rating> addRating(
            @PathVariable Long establishmentId,
            @RequestBody RatingRequest ratingRequest) {

        if (ratingRequest.getUserId() == null) {
            return ResponseEntity.badRequest().body(null); // Handle missing parameter
        }

        Rating rating = ratingRequest.toRating(); // Convert RatingRequest to Rating
        Integer userId = ratingRequest.getUserId();

        Rating savedRating = ratingService.addRating(establishmentId, rating, userId);
        return ResponseEntity.ok(savedRating);
    }
    @PutMapping("/{establishmentId}/{ratingId}")
    public ResponseEntity<Rating> updateRating(
            @PathVariable Long establishmentId,
            @PathVariable Long ratingId,
            @RequestBody Rating updatedRating) {
        try {
            Rating rating = ratingService.updateRating(establishmentId, ratingId, updatedRating);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    private final String uploadDir1 = "src/images/image_establishment";
    @GetMapping("/{establishmentId}/images")
    public ResponseEntity<List<String>> getEstablishmentImages(@PathVariable Long establishmentId) {
        // Get the list of images associated with the establishment ID
        List<Image> images = establishmentService.getImagesByEstablishment(establishmentId);

        if (images.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Create a list of image file names
        List<String> imageFileNames = new ArrayList<>();
        for (Image image : images) {
            System.out.println("Image URL: " + image.getUrl()); // Log the image URL

            // Check if the file exists
            Path filePath = Paths.get(uploadDir1).resolve(image.getUrl()).normalize();
            System.out.println("File Path: " + filePath.toString() + " exists: " + Files.exists(filePath)); // Log the file path and its existence

            if (Files.exists(filePath)) {
                // Add just the file name to the list
                imageFileNames.add(image.getUrl()); // Assuming getUrl() returns just the file name
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(imageFileNames);
    }
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage1(@PathVariable String imageName) {
        File file = new File("src/images/image_establishment/" + imageName);
        Resource resource = new FileSystemResource(file);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }


}
