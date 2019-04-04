package db;

public class PlanDBConnectionFactory {
    // This should change based on the pipeline.
    private static final String DEFAULT_DB = "mysql";
    
    public static PlanDBConnection getConnection(String db) {
        switch (db) {
        case "mysql":
            return new  db.mysql.PlanMySQLConnection();
        default:
            throw new IllegalArgumentException("Invalid database of Plan object:" + db);
        }
    }

    public static PlanDBConnection getConnection() {
        return getConnection(DEFAULT_DB);
    }
}
