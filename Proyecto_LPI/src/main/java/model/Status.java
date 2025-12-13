package model;

import java.time.LocalDateTime;

public class Status {
    private String statusId;
    private String statusName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Status() {
    }

    public Status(String statusId, String statusName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        return statusName;
    }
}