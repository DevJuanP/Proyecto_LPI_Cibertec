package repository;

import java.sql.SQLException;
import java.util.List;

import model.Category;

public interface ICategoryRepository {
    Category findById(String categoryId) throws SQLException, ClassNotFoundException;
    Category findByName(String categoryName) throws SQLException, ClassNotFoundException;
    List<Category> findAll() throws SQLException, ClassNotFoundException;
    void save(Category category) throws SQLException, ClassNotFoundException;
    void update(Category category) throws SQLException, ClassNotFoundException;
    void delete(String categoryId) throws SQLException, ClassNotFoundException;
    boolean existsByName(String categoryName) throws SQLException, ClassNotFoundException;
}