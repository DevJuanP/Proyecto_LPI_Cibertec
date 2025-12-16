package repository;

import java.sql.SQLException;
import java.util.List;

import model.Status;

public interface IStatusRepository {
    Status findById(String statusId) throws SQLException, ClassNotFoundException;
    Status findByName(String statusName) throws SQLException, ClassNotFoundException;
    List<Status> findAll() throws SQLException, ClassNotFoundException;
}