package repository;

import java.sql.SQLException;
import java.util.List;

import model.Author;

public interface IAuthorRepository {
    Author findById(String authorId) throws SQLException, ClassNotFoundException;
    Author findByName(String fullName) throws SQLException, ClassNotFoundException;
    List<Author> findAll() throws SQLException, ClassNotFoundException;
    List<Author> findByCountry(String countryId) throws SQLException, ClassNotFoundException;
    List<Author> findByStatus(String statusId) throws SQLException, ClassNotFoundException;
    void save(Author author) throws SQLException, ClassNotFoundException;
    void update(Author author) throws SQLException, ClassNotFoundException;
    void delete(String authorId) throws SQLException, ClassNotFoundException;
    boolean existsByName(String fullName) throws SQLException, ClassNotFoundException;
}