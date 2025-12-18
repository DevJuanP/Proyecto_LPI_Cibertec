package repository;

import java.sql.SQLException;
import java.util.ArrayList;

import model.BookStatus;

public interface IBookStatusRepository {
    BookStatus findById(String bookStatusId) throws SQLException, ClassNotFoundException;
    BookStatus findByName(String bookStatusName) throws SQLException, ClassNotFoundException;
    ArrayList<BookStatus> findAll() throws SQLException, ClassNotFoundException;
}