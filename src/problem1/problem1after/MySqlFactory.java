package problem1.problem1after;

public class MySqlFactory implements DatabaseFactory {
    @Override
    public DbConnection createConnection() {
        return new MySqlConnection();
    }

    @Override
    public SqlDialect createDialect() {
        return new MySqlDialect();
    }

    @Override
    public SqlEscaper createEscaper() {
        return new MySqlEscaper();
    }
}
