package repository;

import java.sql.SQLException;
import java.util.List;

import model.BookStatus;

public interface IBookStatusRepository {
    BookStatus findById(String bookStatusId) throws SQLException, ClassNotFoundException;
    BookStatus findByName(String bookStatusName) throws SQLException, ClassNotFoundException;
    List<BookStatus> findAll() throws SQLException, ClassNotFoundException;
}