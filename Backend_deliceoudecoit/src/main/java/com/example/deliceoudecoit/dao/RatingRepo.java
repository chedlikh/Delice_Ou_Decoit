package com.example.deliceoudecoit.dao;

import com.example.deliceoudecoit.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RatingRepo extends JpaRepository<Rating,Long> {
    @Query("SELECT COUNT(r) FROM Rating r WHERE r.establishment.id = :establishmentId")
    int countRatingsByEstablishmentId(@Param("establishmentId") Long establishmentId);
}
