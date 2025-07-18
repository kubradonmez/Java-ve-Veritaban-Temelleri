import com.kubra.config.DataBaseConnectorConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        // Önce bağlantıyı başlat
        DataBaseConnectorConfig.setConnection();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "email VARCHAR(100))";

        String insertSql = "INSERT INTO users (name, email) VALUES (?, ?)";
        String updateSql = "UPDATE users SET name = ? WHERE id = ?";
        String deleteSql = "DELETE FROM users WHERE id = ?";

        try {
            Connection connection = DataBaseConnectorConfig.getConnection();

            // Tablo oluşturma
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            System.out.println("Tablo oluşturuldu");

            // Veri ekleme
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, "Ali");
            preparedStatement.setString(2, "ali@mail.com");
            preparedStatement.executeUpdate();
            System.out.println("Veri eklendi");

            //Veri listeleme
            String selectSql = "SELECT * FROM users where email = ?";
            PreparedStatement prepared = connection.prepareStatement(selectSql);
            prepared.setString(1, "ali@mail.com");
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("email"));
            }
            // Veri Güncelleme
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, "Ahmet");
            updateStatement.setInt(2, 1);
            int updatedRows = updateStatement.executeUpdate();
            System.out.println("Güncellenen satır sayısı: " + updatedRows);

            // Silme
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, 1);
            int deletedRows = deleteStatement.executeUpdate();
            System.out.println("Silinen satır sayısı: " + deletedRows);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Bağlantıyı kapat
            DataBaseConnectorConfig.closeConnection();
        }
    }
}