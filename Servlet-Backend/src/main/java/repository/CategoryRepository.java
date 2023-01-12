package repository;

import dto.CategoryDTO;
import dto.ItemDTO;
import util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepository {
    Connection connection;

    private void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", Constants.env.get("MYSQL_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public CategoryDTO getCategory(int id) throws SQLException {
        createConnection();
        String sql = "SELECT * FROM categories WHERE id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        ResultSet resultSet = prepareStatement.executeQuery();
        CategoryDTO category = null;

        while (resultSet.next()) {
            int categoryId = resultSet.getInt(1);
            String title = resultSet.getString(2);
            category = new CategoryDTO(categoryId, title);
        }
        terminateConnection();
        return category;
    }

    public List<CategoryDTO> getAllCategories() throws SQLException {
        createConnection();
        String sql = "SELECT c.id as category_id, c.title as category, i.id as item_id, i.title, i.description, i.price FROM categories c LEFT JOIN items i ON c.id = i.category_id;";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);

        Map<Integer, CategoryDTO> categoryMap = new HashMap<>();
        while (resultSet.next()) {
            int categoryId = resultSet.getInt(1);
            String category = resultSet.getString(2);
            int itemId = resultSet.getInt(3);
            String itemTitle = resultSet.getString(4);
            String itemDescription = resultSet.getString(5);
            double itemPrice = resultSet.getDouble(6);

            if (categoryMap.containsKey(categoryId)) {
                CategoryDTO categoryDTO = categoryMap.get(categoryId);
                if (itemId != 0) {
                    categoryDTO.getItems().add(new ItemDTO(itemId, itemTitle, itemDescription, itemPrice));
                }
            } else {
                List<ItemDTO> list = new ArrayList<>();
                if (itemId != 0) {
                    list.add(new ItemDTO(itemId, itemTitle, itemDescription, itemPrice));
                }
                categoryMap.put(categoryId, new CategoryDTO(categoryId, category, list));
            }
        }
        List<CategoryDTO> categories = new ArrayList<>(categoryMap.values());

        terminateConnection();
        return categories;
    }

    public void addCategory(String title) throws SQLException {
        createConnection();
        String sql = "Insert into categories (title) VALUES (?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, title);
        prepareStatement.executeUpdate();
        terminateConnection();
    }

    private void terminateConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
