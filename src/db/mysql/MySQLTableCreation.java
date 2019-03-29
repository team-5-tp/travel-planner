package db.mysql;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
    public static void main(String[] args) {
        try {
            // Step 1 Connect to MySQL.
            System.out.println("Connecting to " + MySQLDBUtil.URL);
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
            
            if (conn == null) {
                return;
            }
            // Step 2 Drop tables in case they exist.
            Statement statement = conn.createStatement();
            String sql = "DROP TABLE IF EXISTS plan";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);

            // Step 3 Create new tables
            // 1. user
            sql = "CREATE TABLE user ("
                    + "user_id INT AUTO_INCREMENT,"
                    + "username NVARCHAR(255) NOT NULL,"
                    + "password NVARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (user_id),"
                    + "UNIQUE KEY (username)"
                    + ")";
            statement.executeUpdate(sql);
            
            // 2. plan
            sql = "CREATE TABLE plan ("
                    + "plan_id INT AUTO_INCREMENT,"
                    + "planname NVARCHAR(255) NOT NULL,"
                    + "user_id INT,"
                    + "PRIMARY KEY (plan_id),"
                    + "FOREIGN KEY (user_id) REFERENCES user(user_id)"
                    + ")";
            statement.execute(sql);

            // Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
            // 1. user
            sql = "INSERT IGNORE INTO user VALUES(NULL,'1111', '2222')";
            statement.executeUpdate(sql);
            
            // 2. plan
            // Insert a fake plan
            sql = "INSERT IGNORE INTO plan VALUES(NULL, 'LA', 1)";
            statement.executeUpdate(sql); 

            conn.close();
            System.out.println("Import done successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
