package com.kubra.dao;

import com.kubra.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements  UserDao {
    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createtable() {
        String sql = "CREATE TABLE IF NOT EXISTS userss (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(100)," +
                "email VARCHAR(100))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Tablo oluşturuldu");
        } catch (SQLException e) {
            throw new RuntimeException("Tablo Oluşturulmadı");
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO userss (name,email) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();
            System.out.println("Kayıt tamamlandı");
        } catch (SQLException e) {
            throw new RuntimeException("kayıtta problem");
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM userss";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Kullanıcılar getirilemedi", e);
        }
        return users;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE userss SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setInt(3, user.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Güncellenecek kullanıcı bulunamadı.");
            } else {
                System.out.println("Kullanıcı güncellendi.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Güncelleme sırasında hata oluştu", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM userss WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Silinecek kullanıcı bulunamadı.");
            } else {
                System.out.println("Kullanıcı silindi.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Silme sırasında hata oluştu", e);
        }
    }
}
