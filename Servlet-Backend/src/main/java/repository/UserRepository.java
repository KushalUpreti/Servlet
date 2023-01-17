package repository;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends DBConnection {

    public List<String> getUserRoles(String email) {
        createConnection();
        List<String> roles = new ArrayList<>();
        try {
            String sql = "SELECT r.title as role from servlet.roles r inner join servlet.user_roles u\n" +
                    "on r.id = u.role_id inner join servlet.users\n" +
                    "on users.id = u.user_id WHERE users.email = ?";
            PreparedStatement prepareStatement = getConnection().prepareStatement(sql);
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
}
