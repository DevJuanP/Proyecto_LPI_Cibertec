package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DTO que representa las estadísticas de alquiler de un libro.
 */
public class BookRentalStats {
    private String bookId;
    private String isbn;
    private String title;
    private String authorId;
    private String authorName;
    private String categoryName;
    private int totalRentals;
    private int yesterdayRentals;
    private int todayRentals;
    private Integer rentalCount;
    private BigDecimal trendPercentage;
    private BigDecimal popularityPercentage;
    
    public BookRentalStats() {
    }
    
    public BookRentalStats(String bookId, String isbn, String title, String authorName, 
                          String categoryName, int totalRentals, int yesterdayRentals, int todayRentals) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.totalRentals = totalRentals;
        this.yesterdayRentals = yesterdayRentals;
        this.todayRentals = todayRentals;
    }

    // Getters and Setters
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;
    }

    public Integer getRentalCount() {
        return this.rentalCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalRentals() {
        return totalRentals;
    }

    public void setTotalRentals(int totalRentals) {
        this.totalRentals = totalRentals;
    }

    public int getYesterdayRentals() {
        return yesterdayRentals;
    }

    public void setYesterdayRentals(int yesterdayRentals) {
        this.yesterdayRentals = yesterdayRentals;
    }

    public int getTodayRentals() {
        return todayRentals;
    }

    public void setTodayRentals(int todayRentals) {
        this.todayRentals = todayRentals;
    }

    public BigDecimal getTrendPercentage() {
        return trendPercentage;
    }

    public void setTrendPercentage(BigDecimal trendPercentage) {
        this.trendPercentage = trendPercentage;
    }

    public BigDecimal getPopularityPercentage() {
        return popularityPercentage;
    }

    public void setPopularityPercentage(BigDecimal popularityPercentage) {
        this.popularityPercentage = popularityPercentage;
    }
    
    /**
     * Calcula la tendencia como el cambio porcentual entre ayer y hoy
     */
    public void calculateTrend() {
        if (yesterdayRentals == 0 && todayRentals == 0) {
            this.trendPercentage = BigDecimal.ZERO;
        } else if (yesterdayRentals == 0) {
            this.trendPercentage = new BigDecimal(100);
        } else {
            double change = ((double)(todayRentals - yesterdayRentals) / yesterdayRentals) * 100;
            this.trendPercentage = BigDecimal.valueOf(change)
                                            .setScale(1, RoundingMode.HALF_UP);
        }
    }

    /**
     * Calcula el porcentaje de popularidad relativo al libro más pedido
     */
    public void calculatePopularity(int maxRentals) {
        if (maxRentals == 0) {
            this.popularityPercentage = BigDecimal.ZERO;
        } else {
            double percentage = ((double)totalRentals / maxRentals) * 100;
            this.popularityPercentage = BigDecimal.valueOf(percentage)
                                                .setScale(0, RoundingMode.HALF_UP);
        }
    }
    
    /**
     * Retorna el símbolo de tendencia basado en el porcentaje
     */
    public String getTrendIcon() {
        if (trendPercentage == null) {
            return "bi-dash-circle-fill";
        }
        if (trendPercentage.compareTo(BigDecimal.ZERO) > 0) {
            return "bi-arrow-up-circle-fill";
        } else if (trendPercentage.compareTo(BigDecimal.ZERO) < 0) {
            return "bi-arrow-down-circle-fill";
        }
        return "bi-dash-circle-fill";
    }
    
    /**
     * Retorna la clase CSS para el color de tendencia
     */
    public String getTrendClass() {
        if (trendPercentage == null) {
            return "text-muted";
        }
        if (trendPercentage.compareTo(BigDecimal.ZERO) > 0) {
            return "text-success";
        } else if (trendPercentage.compareTo(BigDecimal.ZERO) < 0) {
            return "text-danger";
        }
        return "text-muted";
    }
}