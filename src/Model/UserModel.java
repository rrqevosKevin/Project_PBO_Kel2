package Model;

import java.util.*;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel implements PenghapusData {

    private int id_user;
    private String username;
    private String password;
    private String no_hp;
    private String role;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean register(UserModel data) throws SQLException {
        Connection conn = (Connection) Connector.configDB();
        String sql = "INSERT INTO users (username, password, no_hp, role) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, data.getUsername());
            pstm.setString(2, data.getPassword());
            pstm.setString(3, data.getNo_hp());
            pstm.setString(4, data.getRole());
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Register: " + e);
            return false;
        }
    }

    public UserModel login(String username, String password) throws SQLException {
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                UserModel user = new UserModel();
                user.setId_user(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    public List<UserModel> tampilUser() throws SQLException {
        List<UserModel> list = new ArrayList<>();
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT * FROM users";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setId_user(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNo_hp(rs.getString("no_hp"));
                user.setRole(rs.getString("role"));
                list.add(user);
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
        return list;
    }

    public List<UserModel> tampilPetugas() throws SQLException {
        List<UserModel> list = new ArrayList<>();
        Connection conn = (Connection) Connector.configDB();
        String sql = "SELECT * FROM users WHERE role = 'petugas'";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                UserModel user = new UserModel();
                user.setId_user(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                list.add(user);
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
        return list;
    }

    public boolean hapusData(int id_user) throws SQLException {
        Connection conn = (Connection) Connector.configDB();
        String sql = "DELETE FROM users WHERE id_user = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id_user);
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Hapus: " + e);
            return false;
        }
    }

    public boolean updateRole(int id_user, String role) throws SQLException {
        Connection conn = (Connection) Connector.configDB();
        String sql = "UPDATE users SET role = ? WHERE id_user = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, role);
            pstm.setInt(2, id_user);
            pstm.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            System.err.println("Gagal Update Role: " + e);
            return false;
        }
    }
}