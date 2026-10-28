package problem1.problem1before;

public class MySqlDialect {
    public String selectTop(String table, int n) {
        return "SELECT * FROM " + table + " LIMIT " + n;
    }
}
