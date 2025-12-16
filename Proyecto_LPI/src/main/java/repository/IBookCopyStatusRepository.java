package repository;

import java.sql.SQLException;
import java.util.List;

import model.BookCopyStatus;

public interface IBookCopyStatusRepository {
    BookCopyStatus findById(String bookCopyStatusId) throws SQLException, ClassNotFoundException;
    BookCopyStatus findByName(String bookCopyStatusName) throws SQLException, ClassNotFoundException;
    List<BookCopyStatus> findAll() throws SQLException, ClassNotFoundException;
}