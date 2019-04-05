package db;

import db.mysql.MySQLConnection;

public class UserDBConnectionFactory {
	// This should change based on the pipeline.
	private static final String DEFAULT_DB = "mysql";
	
	public static UserDBConnection getConnection(String db) {
		switch (db) {
		case "mysql":
			return new  db.mysql.UserMySQLConnection();
		default:
			throw new IllegalArgumentException("Invalid db:" + db);
		}

	}

	public static UserDBConnection getConnection() {
		return getConnection(DEFAULT_DB);
	}
}
