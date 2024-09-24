package com.example.deliceoudecoit.entities;

public class RatingRequest {
    private Integer userId;
    private int productQualityRating;
    private int serviceRating;
    private int hygieneRating;

    // Getters and setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getProductQualityRating() {
        return productQualityRating;
    }

    public void setProductQualityRating(int productQualityRating) {
        this.productQualityRating = productQualityRating;
    }

    public int getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(int serviceRating) {
        this.serviceRating = serviceRating;
    }

    public int getHygieneRating() {
        return hygieneRating;
    }

    public void setHygieneRating(int hygieneRating) {
        this.hygieneRating = hygieneRating;
    }

    // Convert to Rating entity method (if needed)
    public Rating toRating() {
        Rating rating = new Rating();
        rating.setProductQualityRating(this.productQualityRating);
        rating.setServiceRating(this.serviceRating);
        rating.setHygieneRating(this.hygieneRating);
        return rating;
    }
}

