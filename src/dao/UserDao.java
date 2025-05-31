package dao;

import java.sql.*;
import model.User;

public class UserDao {
    
    public boolean registerUser(User user) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            int i = ps.executeUpdate();
            conn.close();
            return i > 0;
        } catch (Exception e) {
            System.out.println("Error in registerUser: " + e);
            return false;
        }
    }

    public User loginUser(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                conn.close();
                return user;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Error in loginUser: " + e);
        }
        return null;
    }
}
