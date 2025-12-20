package model;

import java.time.LocalDateTime;

public class Book {
    private String bookId;
    private String isbn;
    private String title;
    private String authorId;
    private String categoryId;
    private Integer publicationYear;
    private String publisher;
    private Integer pages;
    private String language;
    private String description;
    private String coverImageUrl;
    private String bookStatusId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Author author;
    private Category category;
    private BookStatus bookStatus;

    public Book() {
    }

    public Book(String bookId, String isbn, String title, String authorId, 
                String categoryId, String bookStatusId) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.bookStatusId = bookStatusId;
    }

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getBookStatusId() {
        return bookStatusId;
    }

    public void setBookStatusId(String bookStatusId) {
        this.bookStatusId = bookStatusId;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getPublicationYearString() {
        if (publicationYear == null) {
            return "-";
        }

        if (publicationYear <= 0) {
            int bcYear = -publicationYear + 1;
            return bcYear + " a.C.";
        } else {
            return String.valueOf(publicationYear);
        }
    }

    @Override
    public String toString() {
        return title;
    }
}