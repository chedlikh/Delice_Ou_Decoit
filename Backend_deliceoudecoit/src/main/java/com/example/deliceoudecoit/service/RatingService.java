package com.example.deliceoudecoit.service;
import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.example.deliceoudecoit.dao.RatingRepo;
import com.example.deliceoudecoit.dao.UserRepository;
import com.example.deliceoudecoit.entities.Establishment;
import com.example.deliceoudecoit.entities.Rating;
import com.example.deliceoudecoit.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private EstablishmentRepo establishmentRepo;
    @Autowired
    private UserRepository userRepo;

    public Rating addRating(Long establishmentId, Rating rating, Integer userId) {
        // Fetch establishment and user
        Establishment establishment = establishmentRepo.findById(establishmentId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set user and establishment for the rating
        rating.setUser(user);
        rating.setEstablishment(establishment);

        // Save the rating
        Rating savedRating = ratingRepo.save(rating);
        int ratingCount = ratingRepo.countRatingsByEstablishmentId(establishmentId);
        establishment.setRatingCount(ratingCount);

        // Update the establishment's rates based on all ratings
        establishment.updateAverageRating();
        establishmentRepo.save(establishment);

        return savedRating;
    }
    public Rating updateRating(Long establishmentId, Long ratingId, Rating updatedRating) {
        Establishment establishment = establishmentRepo.findById(establishmentId)
                .orElseThrow(() -> new RuntimeException("Establishment not found"));

        Rating existingRating = ratingRepo.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        // Get the old rating value
        double oldRating = existingRating.getOverallRating();

        // Update the rating details with new values
        existingRating.setProductQualityRating(updatedRating.getProductQualityRating());
        existingRating.setServiceRating(updatedRating.getServiceRating());
        existingRating.setHygieneRating(updatedRating.getHygieneRating());
        existingRating.setOverallRating(updatedRating.getOverallRating());

        // Save the updated rating
        Rating updatedExistingRating = ratingRepo.save(existingRating);

        // Update the total rating in the establishment
        establishment.updateRating(oldRating, updatedRating.getOverallRating());

        // Recalculate the average rating
        establishment.updateAverageRating();
        establishmentRepo.save(establishment);

        return updatedExistingRating;
    }

}
