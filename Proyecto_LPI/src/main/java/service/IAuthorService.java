package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.author.AuthorData;
import dto.shared.PagedResult;
import model.Author;
import model.AuthorStatsData;

public interface IAuthorService {
    PagedResult<AuthorData> getRegisteredAuthors(int page, int pageSize, String search, 
            String countryId, String statusId) throws SQLException, ClassNotFoundException;
    ArrayList<Author> findAll() throws SQLException, ClassNotFoundException;
    int getTotalAuthorsCount() throws SQLException, ClassNotFoundException;
    int getAuthorBookCount(String authorId) throws SQLException, ClassNotFoundException;
    int getActiveAuthorsCount() throws SQLException, ClassNotFoundException;
    Author findById(String authorId) throws SQLException, ClassNotFoundException;
    void save(Author author) throws SQLException, ClassNotFoundException;
    void update(Author author) throws SQLException, ClassNotFoundException;
    void delete(String authorId) throws SQLException, ClassNotFoundException;
    List<AuthorStatsData> getMostRequestedAuthors(
            String countryId, String statusId, int limit) 
            throws SQLException, ClassNotFoundException;
    int getAuthorsWithRentalsCount() throws SQLException, ClassNotFoundException;
    int getTotalAuthorsRentals() throws SQLException, ClassNotFoundException;
}
