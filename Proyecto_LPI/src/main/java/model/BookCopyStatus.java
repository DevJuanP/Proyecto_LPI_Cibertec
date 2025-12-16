package model;

import java.time.LocalDateTime;

public class BookCopyStatus {
    private String bookCopyStatusId;
    private String bookCopyStatusName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BookCopyStatus() {
    }

    public BookCopyStatus(String bookCopyStatusId, String bookCopyStatusName) {
        this.bookCopyStatusId = bookCopyStatusId;
        this.bookCopyStatusName = bookCopyStatusName;
    }

    public BookCopyStatus(String bookCopyStatusId, String bookCopyStatusName,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookCopyStatusId = bookCopyStatusId;
        this.bookCopyStatusName = bookCopyStatusName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getBookCopyStatusId() {
        return bookCopyStatusId;
    }

    public void setBookCopyStatusId(String bookCopyStatusId) {
        this.bookCopyStatusId = bookCopyStatusId;
    }

    public String getBookCopyStatusName() {
        return bookCopyStatusName;
    }

    public void setBookCopyStatusName(String bookCopyStatusName) {
        this.bookCopyStatusName = bookCopyStatusName;
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
        return bookCopyStatusName;
    }
}