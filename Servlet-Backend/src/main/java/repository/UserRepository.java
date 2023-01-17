package repository;

import util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// TODO: Refactor redundant connection code from repositories.
public class UserRepository {
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

    public List<String> getUserRoles(String email) {
        createConnection();
        List<String> roles = new ArrayList<>();
        try {
            String sql = "SELECT r.title as role from servlet.roles r inner join servlet.user_roles u\n" +
                    "on r.id = u.role_id inner join servlet.users\n" +
                    "on users.id = u.user_id WHERE users.email = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, email);
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            terminateConnection();
        }
        return roles;
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
