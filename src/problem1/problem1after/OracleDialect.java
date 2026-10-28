package problem1.problem1after;

public class OracleDialect implements SqlDialect {
    @Override
    public String selectTop(String table, int n) {
        return "SELECT * FROM " + table + " FETCH FIRST " + n + " ROWS ONLY";
    }
}
