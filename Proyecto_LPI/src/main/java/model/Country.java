package model;

import java.time.LocalDateTime;

public class Country {
    private String countryId;
    private String countryName;
    private String countryCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Country() {
    }

    public Country(String countryId, String countryName, String countryCode) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    public Country(String countryId, String countryName, String countryCode, 
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return countryName;
    }
}