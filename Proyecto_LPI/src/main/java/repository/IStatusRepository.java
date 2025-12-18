package repository;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Status;

public interface IStatusRepository {
    Status findById(String statusId) throws SQLException, ClassNotFoundException;
    Status findByName(String statusName) throws SQLException, ClassNotFoundException;
    ArrayList<Status> findAll() throws SQLException, ClassNotFoundException;
}