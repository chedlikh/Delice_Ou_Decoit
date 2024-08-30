package com.example.deliceoudecoit.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("role")
    private String role;
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("DateNaissance")
    private LocalDate datenaissance;
    @JsonProperty("Genre")
    private String gender;
    @JsonProperty("numerotelephone")
    private Integer numberphone;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;
    @JsonProperty("profile_image")
    private String profileImage;

    // Update constructor
    public AuthenticationResponse(String accessToken, String refreshToken, String message, String username, String firstname, String lastname, String role, LocalDate datenaissance, String gender, Integer numberphone, String country, String state, String profileImage) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.datenaissance = datenaissance;
        this.gender = gender;
        this.numberphone = numberphone;
        this.country = country;
        this.state = state;
        this.profileImage = profileImage;
    }

    // Getters and Setters
    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public AuthenticationResponse(String accessToken, String refreshToken, String message, String username, String firstname, String lastname, String role, LocalDate datenaissance, String gender, Integer numberphone, String country, String state) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.datenaissance = datenaissance;
        this.gender = gender;
        this.numberphone = numberphone;
        this.country = country;
        this.state = state;
    }

    // Updated constructor
    public AuthenticationResponse(String accessToken, String refreshToken, String message, String username, String firstname, String lastname, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
    public AuthenticationResponse(String accessToken, String refreshToken, String message) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;

    }

    // Getters
    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(LocalDate datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getnumberphone() {
        return numberphone;
    }

    public void setnumberphone(Integer numberphone) {
        this.numberphone = numberphone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
