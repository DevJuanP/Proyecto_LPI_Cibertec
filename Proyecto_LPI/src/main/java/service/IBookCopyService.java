package service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dto.shared.PagedResult;
import model.Book;
import model.BookCopy;

public interface IBookCopyService {
    BookCopy findById(String bookCopyId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findByBook(String bookId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findByStatus(String bookCopyStatusId) throws SQLException, ClassNotFoundException;
    LinkedList<Book> findAvailableBooks() throws SQLException, ClassNotFoundException;
    List<BookCopy> findAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
    void save(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void saveBatch(String bookId, int quantity, String notes) throws SQLException, ClassNotFoundException;
    void update(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void updateStatusBatch(List<String> bookCopyIds, String newStatusId) throws SQLException, ClassNotFoundException;
    void delete(String bookCopyId) throws SQLException, ClassNotFoundException;
    int countByBook(String bookId) throws SQLException, ClassNotFoundException;
    int countAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
    String getAvailableStatusId() throws SQLException, ClassNotFoundException;
    String getRentedStatusId() throws SQLException, ClassNotFoundException;
    String getMaintenanceStatusId() throws SQLException, ClassNotFoundException;
    String getDiscontinuedStatusId() throws SQLException, ClassNotFoundException;
    PagedResult<BookCopy> getRegisteredBookCopies(int page, int pageSize, String search, 
            String bookId, String bookStatusId) throws SQLException, ClassNotFoundException;
    int getTotalBookCopiesCount() throws SQLException, ClassNotFoundException;
    int getAvailableBookCopiesCount() throws SQLException, ClassNotFoundException;
    int getRentedBookCopiesCount() throws SQLException, ClassNotFoundException;
}