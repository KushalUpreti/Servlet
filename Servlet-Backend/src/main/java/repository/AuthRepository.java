package repository;

import model.User;
import util.Constants;
import util.HTTPUtils;

import java.sql.*;

public class AuthRepository {
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

    public void register(User user) {
        createConnection();
        try {
            String sql = "Insert into users (email,password) VALUES (?, ?)";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            String passwordHash = HTTPUtils.encodePassword(user.getPassword());
            prepareStatement.setString(1, user.getEmail());
            prepareStatement.setString(2, passwordHash);
            prepareStatement.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }finally {
            terminateConnection();
        }
    }

    public User login(User userDTO) {
        User user = null;
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
                user = new User(id, email, password);
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
