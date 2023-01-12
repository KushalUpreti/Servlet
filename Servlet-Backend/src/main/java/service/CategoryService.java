package service;

import com.google.gson.Gson;
import dto.CategoryDTO;
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

    public List<CategoryDTO> getAllCategories() throws SQLException {
        return categoryRepository.getAllCategories();
    }

    public void addCategory(HttpServletRequest request) throws ServletException, SQLException {
        String requestBody = HTTPUtils.jsonParser(request);
        CategoryDTO categoryDTO = gson.fromJson(requestBody, CategoryDTO.class);
        if (Validation.isEmpty(categoryDTO.getTitle())) {
            throw new ServletException("Invalid category name");
        }
        categoryRepository.addCategory(categoryDTO.getTitle());
    }

}
