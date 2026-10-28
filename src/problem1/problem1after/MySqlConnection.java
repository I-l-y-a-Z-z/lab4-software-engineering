package problem1.problem1after;

public class MySqlConnection implements DbConnection {
    @Override
    public String connect() {
        return "Connected to MySQL on port 3306";
    }
}
