package repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.BookRentalStats;
import model.Rental;

public interface IRentalRepository {
    Rental findById(String rentalId) throws SQLException, ClassNotFoundException;
    List<Rental> findAll() throws SQLException, ClassNotFoundException;
    List<Rental> findByUser(String userId) throws SQLException, ClassNotFoundException;
    List<Rental> findByBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException;
    List<Rental> findByStatus(String rentalStatusId) throws SQLException, ClassNotFoundException;
    LinkedList<Rental> findActiveRentals(int offset, int pageSize, String search, String state, Integer dueSoonDays, String fromDate, String toDate) 
                                            throws SQLException, ClassNotFoundException;
    List<Rental> findActiveRentalsByUser(String userId) throws SQLException, ClassNotFoundException;
    List<Rental> findOverdueRentals() throws SQLException, ClassNotFoundException;
    List<Rental> findDueSoon(int days) throws SQLException, ClassNotFoundException;
    void save(Rental rental) throws SQLException, ClassNotFoundException;
    void update(Rental rental) throws SQLException, ClassNotFoundException;
    void delete(String rentalId) throws SQLException, ClassNotFoundException;
    boolean existsActiveRentalForBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException;
    LinkedList<Rental> findPaginated(int offset, int pageSize, String search, String userId, String rentalStatusId) 
                                               throws SQLException, ClassNotFoundException;
    int countAll() throws SQLException, ClassNotFoundException;
    int countByFilters(String search, String userId, String rentalStatusId) 
                               throws SQLException, ClassNotFoundException;
    int countByActiveRentalsFilters(String search, String state, Integer dueSoonDays, String fromDate, String toDate) 
                                        throws SQLException, ClassNotFoundException;
    List<BookRentalStats> getMostRequestedBooks(int offset, int pageSize, String categoryId) 
                                        throws SQLException, ClassNotFoundException;
    int countMostRequestedBooks(String categoryId) throws SQLException, ClassNotFoundException;
    List<Rental> findRecentRentals(int limit) throws SQLException, ClassNotFoundException;
    List<BookRentalStats> findTopRequestedBooks(int limit) throws SQLException, ClassNotFoundException;
}