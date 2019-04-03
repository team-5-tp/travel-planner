package db;


public class PoiDBConnectionFactory {
    // This should change based on the pipeline.
    private static final String DEFAULT_DB = "mysql";
    
    public static PoiDBConnection getConnection(String db) {
        switch (db) {
        case "mysql":
            return new  db.mysql.PoiMySQLConnection();
        default:
            throw new IllegalArgumentException("Invalid database of Plan object:" + db);
        }
    }

    public static PoiDBConnection getConnection() {
        return getConnection(DEFAULT_DB);
    }
}