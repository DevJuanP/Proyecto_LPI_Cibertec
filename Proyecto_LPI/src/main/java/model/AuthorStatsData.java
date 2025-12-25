package model;

/**
 * DTO para estadísticas de autores más pedidos.
 * Contiene información agregada sobre alquileres por autor.
 */
public class AuthorStatsData {
    private String authorId;
    private String fullName;
    private String pseudonym;
    private String photoUrl;
    private String countryName;
    private int totalBooks;
    private int totalCopies;
    private int availableCopies;
    private int totalRentals;
    private double avgRentalsPerBook;
    private double availabilityRate;
    
    public AuthorStatsData() {
    }
    
    public AuthorStatsData(String authorId, String fullName, String pseudonym, String photoUrl, String countryName,
                          int totalBooks, int totalCopies, int availableCopies, int totalRentals) {
        this.authorId = authorId;
        this.fullName = fullName;
        this.pseudonym = pseudonym;
        this.photoUrl = photoUrl;
        this.countryName = countryName;
        this.totalBooks = totalBooks;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.totalRentals = totalRentals;
        
        // Calcular métricas derivadas
        this.avgRentalsPerBook = totalBooks > 0 ? (double) totalRentals / totalBooks : 0.0;
        this.availabilityRate = totalCopies > 0 ? (double) availableCopies / totalCopies * 100 : 0.0;
    }
    
    // Getters y setters
    public String getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPseudonym() {
        return pseudonym;
    }
    
    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public int getTotalBooks() {
        return totalBooks;
    }
    
    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
        recalculateMetrics();
    }
    
    public int getTotalCopies() {
        return totalCopies;
    }
    
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        recalculateMetrics();
    }
    
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
        recalculateMetrics();
    }
    
    public int getTotalRentals() {
        return totalRentals;
    }
    
    public void setTotalRentals(int totalRentals) {
        this.totalRentals = totalRentals;
        recalculateMetrics();
    }
    
    public double getAvgRentalsPerBook() {
        return avgRentalsPerBook;
    }
    
    public double getAvailabilityRate() {
        return availabilityRate;
    }
    
    /**
     * Recalcula las métricas derivadas cuando cambian los valores base.
     */
    private void recalculateMetrics() {
        this.avgRentalsPerBook = totalBooks > 0 ? (double) totalRentals / totalBooks : 0.0;
        this.availabilityRate = totalCopies > 0 ? (double) availableCopies / totalCopies * 100 : 0.0;
    }
}