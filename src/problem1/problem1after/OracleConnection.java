package problem1.problem1after;

public class OracleConnection implements DbConnection {
    @Override
    public String connect() {
        return "Connected to Oracle on port 1521";
    }
}
