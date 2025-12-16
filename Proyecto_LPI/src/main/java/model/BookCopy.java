package model;

import java.time.LocalDateTime;

public class BookCopy {
    private String bookCopyId;
    private String bookId;
    private String bookCopyStatusId;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Book book;
    private BookCopyStatus bookCopyStatus;

    public BookCopy() {
    }

    public BookCopy(String bookCopyId, String bookId, String bookCopyStatusId, Integer copyNumber) {
        this.bookCopyId = bookCopyId;
        this.bookId = bookId;
        this.bookCopyStatusId = bookCopyStatusId;
    }

    public String getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(String bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookCopyStatusId() {
        return bookCopyStatusId;
    }

    public void setBookCopyStatusId(String bookCopyStatusId) {
        this.bookCopyStatusId = bookCopyStatusId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookCopyStatus getBookCopyStatus() {
        return bookCopyStatus;
    }

    public void setBookCopyStatus(BookCopyStatus bookCopyStatus) {
        this.bookCopyStatus = bookCopyStatus;
    }

    @Override
    public String toString() {
        return book != null ? book.getTitle() + " - Copia #" + bookCopyId : "Copia #" + bookCopyId;
    }
}