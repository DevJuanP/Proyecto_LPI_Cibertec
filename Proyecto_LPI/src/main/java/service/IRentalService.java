package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import dto.shared.PagedResult;
import model.BookRentalStats;
import model.Rental;
import model.RentalStatus;

public interface IRentalService {
    Rental findById(String rentalId) throws SQLException, ClassNotFoundException;
    List<Rental> findAll() throws SQLException, ClassNotFoundException;
    PagedResult<Rental> getRegisteredRentals(int page, int pageSize, String search, 
                                               String userId, String rentalStatusId) 
                                               throws SQLException, ClassNotFoundException;
    
    void createRental(String userId, String bookId, int rentalDays, String notes) 
                      throws SQLException, ClassNotFoundException;
    void markAsReturned(String rentalId) throws SQLException, ClassNotFoundException;
    void cancelRental(String rentalId) throws SQLException, ClassNotFoundException;
    
    int getTotalRentalsCount() throws SQLException, ClassNotFoundException;
    int getActiveRentalsCount() throws SQLException, ClassNotFoundException;
    int getOverdueRentalsCount() throws SQLException, ClassNotFoundException;
    int getDueSoonRentalsCount(int days) throws SQLException, ClassNotFoundException;
    int getOnTimeRentalsCount(int dueSoonDays) throws SQLException, ClassNotFoundException;
    
    PagedResult<Rental> findActiveRentals(int page, int pageSize, String search, String state, Integer dueSoonDays, String fromDate, String toDate)
                                                      throws SQLException, ClassNotFoundException;    
    List<Rental> findOverdueRentals() throws SQLException, ClassNotFoundException;
    List<Rental> findDueSoonRentals(int days) throws SQLException, ClassNotFoundException;
    
    List<RentalStatus> getAllRentalStatuses() throws SQLException, ClassNotFoundException;

    PagedResult<BookRentalStats> getMostRequestedBooks(int page, int pageSize, String categoryId) 
                                throws SQLException, ClassNotFoundException;
    
    BigDecimal getDefaultDailyRate();
}