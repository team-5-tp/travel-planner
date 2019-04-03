package db.mysql;

import db.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
public class MySQLConnection implements DBConnection {
	protected Connection conn;
	public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
