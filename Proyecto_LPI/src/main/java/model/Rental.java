package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Rental {
    private String rentalId;
    private String userId;
    private String bookCopyId;
    private String rentalStatusId;
    private LocalDateTime rentalDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Integer rentalDays;
    private BigDecimal dailyRate;
    private BigDecimal totalCost;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private User user;
    private BookCopy bookCopy;
    private RentalStatus rentalStatus;

    public Rental() {
    }

    public Rental(String rentalId, String userId, String bookCopyId, String rentalStatusId,
                  LocalDateTime rentalDate, LocalDateTime dueDate, Integer rentalDays,
                  BigDecimal dailyRate, BigDecimal totalCost) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.bookCopyId = bookCopyId;
        this.rentalStatusId = rentalStatusId;
        this.rentalDate = rentalDate;
        this.dueDate = dueDate;
        this.rentalDays = rentalDays;
        this.dailyRate = dailyRate;
        this.totalCost = totalCost;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(String bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public String getRentalStatusId() {
        return rentalStatusId;
    }

    public void setRentalStatusId(String rentalStatusId) {
        this.rentalStatusId = rentalStatusId;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    @Override
    public String toString() {
        return "Rental #" + rentalId;
    }
}