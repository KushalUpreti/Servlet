package service;

import com.google.gson.Gson;
import model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import repository.CategoryRepository;
import util.HTTPUtils;
import util.Validation;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Gson gson;

    public CategoryService() {
        this.gson = new Gson();
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryRepository.getAllCategories();
    }

    public void addCategory(HttpServletRequest request) throws ServletException, SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        Category category = gson.fromJson(requestBody, Category.class);
        if (Validation.isEmpty(category.getTitle())) {
            throw new ServletException("Invalid category name");
        }
        categoryRepository.addCategory(category.getTitle());
    }

}
