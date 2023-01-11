package repository;

import dto.ItemDTO;
import util.Constants;

import java.sql.*;

public class ItemRepository {
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

    public ItemDTO addItem(ItemDTO dto) throws SQLException {
        createConnection();
        String sql = "Insert into items (title,description,price,category_id) VALUES (?,?,?,?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, dto.getTitle());
        prepareStatement.setString(2, dto.getDescription());
        prepareStatement.setDouble(3, dto.getPrice());
        prepareStatement.setInt(4, dto.getCategoryId());
        prepareStatement.executeUpdate();

        ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            dto.setId(generatedKeys.getInt(1));
        }
        terminateConnection();
        return dto;
    }

    public ItemDTO getItem(int itemId) throws SQLException{
        createConnection();
        String sql = "SELECT * from items where id = ?";
        PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setInt(1, itemId);
        ResultSet resultSet = prepareStatement.executeQuery();
        ItemDTO itemDTO = null;
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            int categoryId = resultSet.getInt(5);
            itemDTO = new ItemDTO(id,title,description,categoryId,price);
        }
        return itemDTO;
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
