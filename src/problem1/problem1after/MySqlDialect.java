package problem1.problem1after;

public class MySqlDialect implements SqlDialect {
    @Override
    public String selectTop(String table, int n) {
        return "SELECT * FROM " + table + " LIMIT " + n;
    }
}
