package com.example.deliceoudecoit.entities;

import com.example.deliceoudecoit.dao.EstablishmentRepo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "establishment")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(nullable = false)
    private String name;
    private String description;
    private String nameId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    @OneToMany(mappedBy = "establishment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Rating> ratings;
    @Column(name = "card_image")
    private String cardImage;

    private double rates;
    private int ratingCount;

    public double getRates() {
        return rates;
    }

    public void setRates(double rates) {
        this.rates = rates;
    }

    // Optional method to update rates
    public double getAverageRating() {
        if (ratingCount == 0) {
            return 0.0; // Avoid division by zero
        }
        return rates / ratingCount;
    }
    public void updateAverageRating() {
        this.rates = calculateAverageRating();
    }
    public double calculateAverageRating() {
        return ratings.stream()
                .mapToInt(Rating::getOverallRating)
                .average()
                .orElse(0.0);
    }

    public void addToRatings(double newRating) {
        this.rates += newRating;
        this.ratingCount++;
    }

    public void updateRating(double oldRating, double newRating) {
        this.rates = this.rates - oldRating + newRating;
    }
    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getNameId() {
        return nameId;
    }




}
