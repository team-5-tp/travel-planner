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
			String sql = "DROP TABLE IF EXISTS user";
			statement.executeUpdate(sql);

			// Step 3 Create new tables
			sql = "CREATE TABLE user ("
					+ "user_id INT AUTO_INCREMENT,"
					+ "username NVARCHAR(255) NOT NULL,"
					+ "password NVARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (user_id),"
					+ "UNIQUE KEY (username)"
					+ ")";
			statement.executeUpdate(sql);
			
			
	
			
			// create point of interest table
			sql = "DROP TABLE IF EXISTS poi";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE poi ("
					+ "poi_id INT AUTO_INCREMENT,"
					+ "poi_name NVARCHAR(255) NOT NULL,"
					+ "visiting_order INT"
					+ "plan_id INT,"
					+ "venue_id NVARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (poi_id)"
					+ "user_id INT"
					+ ")";
			statement.executeUpdate(sql);


			// Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
			sql = "INSERT IGNORE INTO user VALUES(NULL,'1111', '2222')";
			statement.executeUpdate(sql);
			// insert a fake poi
			
			sql = "INSERT IGNORE INTO poi VALUES(NULL, 'LA', 1, 2, NULL)";
			statement.executeUpdate(sql); 


			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}