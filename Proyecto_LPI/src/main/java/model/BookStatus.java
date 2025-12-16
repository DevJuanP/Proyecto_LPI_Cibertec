package model;

import java.time.LocalDateTime;

public class BookStatus {
    private String bookStatusId;
    private String bookStatusName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookStatus() {
    }

    public BookStatus(String bookStatusId, String bookStatusName) {
        this.bookStatusId = bookStatusId;
        this.bookStatusName = bookStatusName;
    }

    public BookStatus(String bookStatusId, String bookStatusName,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookStatusId = bookStatusId;
        this.bookStatusName = bookStatusName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getBookStatusId() {
        return bookStatusId;
    }

    public void setBookStatusId(String bookStatusId) {
        this.bookStatusId = bookStatusId;
    }

    public String getBookStatusName() {
        return bookStatusName;
    }

    public void setBookStatusName(String bookStatusName) {
        this.bookStatusName = bookStatusName;
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
        return bookStatusName;
    }
}