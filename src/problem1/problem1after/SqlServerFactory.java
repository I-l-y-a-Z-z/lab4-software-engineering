package problem1.problem1after;

public class SqlServerFactory implements DatabaseFactory {
    @Override
    public DbConnection createConnection() {
        return new SqlServerConnection();
    }

    @Override
    public SqlDialect createDialect() {
        return new SqlServerDialect();
    }

    @Override
    public SqlEscaper createEscaper() {
        return new SqlServerEscaper();
    }
}
