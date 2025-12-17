package dto.author;

import java.time.LocalDateTime;

public class AuthorData {
    private final String authorId;
    private final String fullName;
    private final String pseudonym;
    private final String biography;
    private final String birthDate;
    private final String deathDate;
    private final String website;
    private final String email;
    private final String photoUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String countryId;
    private final String countryName;
    private final String statusId;
    private final String statusName;

    public AuthorData(String authorId, String fullName, String pseudonym, 
                      String biography, String birthDate, String deathDate,
                      String website, String email, String photoUrl,
                      LocalDateTime createdAt, LocalDateTime updatedAt,
                      String countryId, String countryName, 
                      String statusId, String statusName) {
        this.authorId = authorId;
        this.fullName = fullName;
        this.pseudonym = pseudonym;
        this.biography = biography;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.website = website;
        this.email = email;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.countryId = countryId;
        this.countryName = countryName;
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public String getAuthorId() { return authorId; }
    public String getFullName() { return fullName; }
    public String getPseudonym() { return pseudonym; }
    public String getBiography() { return biography; }
    public String getBirthDate() { return birthDate; }
    public String getDeathDate() { return deathDate; }
    public String getWebsite() { return website; }
    public String getEmail() { return email; }
    public String getPhotoUrl() { return photoUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCountryId() { return countryId; }
    public String getCountryName() { return countryName; }
    public String getStatusId() { return statusId; }
    public String getStatusName() { return statusName; }
}