package service;

import java.sql.SQLException;
import java.util.ArrayList;

import model.BookStatus;

public interface IBookStatusService {
    String getActiveStatusId() throws SQLException, ClassNotFoundException;

    String getInactiveStatusId() throws SQLException, ClassNotFoundException;

    ArrayList<BookStatus> findAll() throws SQLException, ClassNotFoundException;
}
