CREATE TABLE BookCopy (
    BookCopyId BINARY(16) PRIMARY KEY,
    BookId BINARY(16) NOT NULL,
    BookCopyStatusId BINARY(16) NOT NULL,
    Notes TEXT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_bookcopy_book FOREIGN KEY (BookId) REFERENCES Book(BookId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_bookcopy_status FOREIGN KEY (BookCopyStatusId) REFERENCES BookCopyStatus(BookCopyStatusId) ON UPDATE CASCADE,
    INDEX idx_bookcopy_book (BookId),
    INDEX idx_bookcopy_status (BookCopyStatusId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
