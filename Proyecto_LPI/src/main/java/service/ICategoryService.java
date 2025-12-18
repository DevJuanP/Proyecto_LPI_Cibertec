package service;

import java.sql.SQLException;
import java.util.List;

import model.Category;

public interface ICategoryService {
    List<Category> findAll() throws SQLException, ClassNotFoundException;
}
