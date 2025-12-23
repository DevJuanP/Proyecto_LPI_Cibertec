package service;

import java.sql.SQLException;
import java.util.List;

import model.BookCopyStatus;

public interface IBookCopyStatusService {
    List<BookCopyStatus> findAll() throws SQLException, ClassNotFoundException;
}
