package repository;

import dto.CategoryDTO;
import util.Constants;

import java.sql.*;
import java.util.ArrayList;
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

    public List<CategoryDTO> getAllCategories() throws SQLException {
        createConnection();
        String sql = "SELECT * FROM categories";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);

        List<CategoryDTO> categories = new ArrayList<>();
        while (resultSet.next()) {
            int categoryId = resultSet.getInt(1);
            String title = resultSet.getString(2);
            categories.add(new CategoryDTO(categoryId, title));
        }
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
