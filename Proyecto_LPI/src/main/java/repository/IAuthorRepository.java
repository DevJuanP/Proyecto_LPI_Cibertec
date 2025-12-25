package repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Author;
import model.AuthorStatsData;

public interface IAuthorRepository {
    Author findById(String authorId) throws SQLException, ClassNotFoundException;

    Author findByName(String fullName) throws SQLException, ClassNotFoundException;

    LinkedList<Author> findAll() throws SQLException, ClassNotFoundException;

    LinkedList<Author> findByCountry(String countryId) throws SQLException, ClassNotFoundException;

    LinkedList<Author> findByStatus(String statusId) throws SQLException, ClassNotFoundException;

    void save(Author author) throws SQLException, ClassNotFoundException;

    void update(Author author) throws SQLException, ClassNotFoundException;

    void delete(String authorId) throws SQLException, ClassNotFoundException;

    boolean existsByName(String fullName) throws SQLException, ClassNotFoundException;

    int count() throws SQLException, ClassNotFoundException;

    int count(String search, String countryId, String statusId) throws SQLException, ClassNotFoundException;
    
    int countAuthorBooks(String authorId) throws SQLException, ClassNotFoundException;

    LinkedList<Author> findAllPaginated(int offset, int limit) throws SQLException, ClassNotFoundException;

    LinkedList<Author> findAllPaginated(int offset, int limit, String search, String countryId, String statusId)
            throws SQLException, ClassNotFoundException;

    List<AuthorStatsData> getMostRequestedAuthors(
            String countryId, String statusId, int limit) 
            throws SQLException, ClassNotFoundException;

    int countAuthorsWithRentals() throws SQLException, ClassNotFoundException;

    int getTotalAuthorsRentals() throws SQLException, ClassNotFoundException;
}