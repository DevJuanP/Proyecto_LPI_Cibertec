package repository;

import java.sql.SQLException;
import java.util.List;

import model.Rental;

public interface IRentalRepository {
    Rental findById(String rentalId) throws SQLException, ClassNotFoundException;
    List<Rental> findAll() throws SQLException, ClassNotFoundException;
    List<Rental> findByUser(String userId) throws SQLException, ClassNotFoundException;
    List<Rental> findByBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException;
    List<Rental> findByStatus(String rentalStatusId) throws SQLException, ClassNotFoundException;
    List<Rental> findActiveRentals() throws SQLException, ClassNotFoundException;
    List<Rental> findActiveRentalsByUser(String userId) throws SQLException, ClassNotFoundException;
    List<Rental> findOverdueRentals() throws SQLException, ClassNotFoundException;
    List<Rental> findDueSoon(int days) throws SQLException, ClassNotFoundException;
    void save(Rental rental) throws SQLException, ClassNotFoundException;
    void update(Rental rental) throws SQLException, ClassNotFoundException;
    void delete(String rentalId) throws SQLException, ClassNotFoundException;
    boolean existsActiveRentalForBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException;
}