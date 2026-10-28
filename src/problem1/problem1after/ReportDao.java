package problem1.problem1after;

public class ReportDao {
    private final DbConnection connection;
    private final SqlDialect dialect;

    public ReportDao(DatabaseFactory factory) {
        connection = factory.createConnection();
        dialect = factory.createDialect();
    }

    public void topStudents(int n) {
        System.out.println(connection.connect());
        System.out.println(dialect.selectTop("students", n));
    }
}
