import com.kubra.config.DataBaseConfig;
import com.kubra.config.DataBaseConnectorConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String sql="CREATE TABLE IF NOT EXISTS users ("+
                "id SERIAL PRIMARY KEY,"+
                "name VARCHAR(100),"+
                "email VARCHAR(100))";
        try{
            Connection connection= DriverManager.getConnection(DataBaseConfig.DATABASE_URL,
                    DataBaseConfig.DATABASE_USERNAME, DataBaseConfig.DATABASE_PASSWORD);
            Statement statement=connection.createStatement();
            statement.execute(sql);
            System.out.println("Table Created");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
