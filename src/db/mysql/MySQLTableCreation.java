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
            String sql = "DROP TABLE IF EXISTS poi";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS plan";
            statement.executeUpdate(sql);
            sql = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sql);

            // Step 3 Create new tables
            // 1. user
            sql = "CREATE TABLE user ("
                    + "id INT AUTO_INCREMENT,"
                    + "username NVARCHAR(255) NOT NULL,"
                    + "password NVARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (id),"
                    + "UNIQUE KEY (username)"
                    + ")";
            statement.executeUpdate(sql);
            
            // 2. plan
            sql = "CREATE TABLE plan ("
                    + "id INT AUTO_INCREMENT,"
                    + "name NVARCHAR(255) NOT NULL,"
                    + "user_id INT,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (user_id) REFERENCES user(id)"
                    + ")";
            statement.execute(sql);
            
            // 3. poi
            sql = "CREATE TABLE poi ("
                    + "id INT AUTO_INCREMENT,"
                    + "name NVARCHAR(255) NOT NULL,"
                    + "visiting_order INT"
                    + "plan_id INT,"
                    + "venue_id NVARCHAR(255) NOT NULL,"
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (plan_id) REFERENCES plan(id)"
                    + ")";
            statement.executeUpdate(sql);

            // Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
            // 1. user
            sql = "INSERT IGNORE INTO user (username, password) VALUES('1111', '2222')";
            statement.executeUpdate(sql);
            
            // 2. plan
            // Insert a fake plan
            sql = "INSERT IGNORE INTO plan (name, user_id) VALUES('LA', 1)";
            statement.executeUpdate(sql);
            
            // 3. poi
            // Insert two fake points: universal studio, getty
            sql = "INSERT IGNORE INTO poi (name, visiting_order, plan_id, venue_id) "
                    + "VALUES('universal', 1, 1, 1)";
            statement.executeUpdate(sql);

            conn.close();
            System.out.println("Successfully created tables");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
