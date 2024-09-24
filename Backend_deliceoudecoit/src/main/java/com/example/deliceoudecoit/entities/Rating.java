package com.example.deliceoudecoit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "establishment_id", nullable = false)
    private Establishment establishment;

    @Column(nullable = false)
    private int productQualityRating;

    @Column(nullable = false)
    private int serviceRating;

    @Column(nullable = false)
    private int hygieneRating;

    @Column(nullable = false)
    private int overallRating;

    // Calculate the overall rating based on other ratings
    @PrePersist
    @PreUpdate
    private void calculateOverallRating() {
        this.overallRating = (productQualityRating + serviceRating + hygieneRating) / 3;
    }

    // Getters and Setters
}
