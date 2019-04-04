package db;

public class PoIDBConnectionFactory {
    // This should change based on the pipeline.
    private static final String DEFAULT_DB = "mysql";
    
    public static PoIDBConnection getConnection(String db) {
        switch (db) {
        case "mysql":
            return new  db.mysql.PoIMySQLConnection();
        default:
            throw new IllegalArgumentException("Invalid database of Plan object:" + db);
        }
    }

    public static PoIDBConnection getConnection() {
        return getConnection(DEFAULT_DB);
    }
}
