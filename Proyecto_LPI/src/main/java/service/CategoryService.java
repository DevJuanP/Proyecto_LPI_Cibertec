package service;

import java.sql.SQLException;
import java.util.List;

import model.Category;
import repository.ICategoryRepository;

public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() throws SQLException, ClassNotFoundException {
        return categoryRepository.findAll();
    }
}
