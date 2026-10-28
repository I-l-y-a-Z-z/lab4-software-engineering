package problem1.problem1before;

public class SqlServerDialect {
    public String selectTop(String table, int n) {
        return "SELECT TOP " + n + " * FROM " + table;
    }
}
