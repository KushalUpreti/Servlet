package repository;

import dto.UserDTO;
import utils.HTTPUtils;

import java.sql.*;
import java.util.Map;

public class AuthRepository {
    Connection connection;

    private void createConnection() {
        Map<String, String> env = System.getenv();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", env.get("MYSQL_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void register(UserDTO userDTO) throws SQLException {
        createConnection();
        String sql = "Insert into users (email,password) VALUES (?, ?)";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        String passwordHash = HTTPUtils.encodePassword(userDTO.getPassword());
        prepareStatement.setString(1, userDTO.getEmail());
        prepareStatement.setString(2, passwordHash);
        prepareStatement.executeUpdate();
        terminateConnection();
    }

    public UserDTO login(UserDTO userDTO) {
        UserDTO user = null;
        try {
            createConnection();
            String sql = "SELECT * FROM USERS WHERE email = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, userDTO.getEmail());
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new UserDTO(id, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return user;
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
