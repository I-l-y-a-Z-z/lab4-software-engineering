package problem1.problem1after;

public interface DatabaseFactory {
    DbConnection createConnection();

    SqlDialect createDialect();

    SqlEscaper createEscaper();

    static DatabaseFactory forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Unknown database: null");
        }
        return switch (name) {
            case "mysql" -> new MySqlFactory();
            case "sqlserver" -> new SqlServerFactory();
            case "oracle" -> new OracleFactory();
            default -> throw new IllegalArgumentException("Unknown database: " + name);
        };
    }
}
