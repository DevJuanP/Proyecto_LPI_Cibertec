package service;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Status;

public interface IStatusService {
    String getActiveStatusId() throws SQLException, ClassNotFoundException;

    String getInactiveStatusId() throws SQLException, ClassNotFoundException;

    ArrayList<Status> findAll() throws SQLException, ClassNotFoundException;
}
