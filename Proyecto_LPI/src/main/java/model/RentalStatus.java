package model;

import java.time.LocalDateTime;

public class RentalStatus {
    private String rentalStatusId;
    private String rentalStatusName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalStatus() {
    }

    public RentalStatus(String rentalStatusId, String rentalStatusName) {
        this.rentalStatusId = rentalStatusId;
        this.rentalStatusName = rentalStatusName;
    }

    public RentalStatus(String rentalStatusId, String rentalStatusName,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.rentalStatusId = rentalStatusId;
        this.rentalStatusName = rentalStatusName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getRentalStatusId() {
        return rentalStatusId;
    }

    public void setRentalStatusId(String rentalStatusId) {
        this.rentalStatusId = rentalStatusId;
    }

    public String getRentalStatusName() {
        return rentalStatusName;
    }

    public void setRentalStatusName(String rentalStatusName) {
        this.rentalStatusName = rentalStatusName;
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
        return rentalStatusName;
    }
}