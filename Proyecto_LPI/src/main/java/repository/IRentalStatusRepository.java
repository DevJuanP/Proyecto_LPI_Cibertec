package repository;

import java.sql.SQLException;
import java.util.List;

import model.RentalStatus;

public interface IRentalStatusRepository {
    RentalStatus findById(String rentalStatusId) throws SQLException, ClassNotFoundException;
    RentalStatus findByName(String rentalStatusName) throws SQLException, ClassNotFoundException;
    List<RentalStatus> findAll() throws SQLException, ClassNotFoundException;
}