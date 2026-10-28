package problem1.problem1after;

public class SqlServerDialect implements SqlDialect {
    @Override
    public String selectTop(String table, int n) {
        return "SELECT TOP " + n + " * FROM " + table;
    }
}
