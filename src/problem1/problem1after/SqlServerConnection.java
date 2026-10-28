package problem1.problem1after;

public class SqlServerConnection implements DbConnection {
    @Override
    public String connect() {
        return "Connected to SQL Server on port 1433";
    }
}
