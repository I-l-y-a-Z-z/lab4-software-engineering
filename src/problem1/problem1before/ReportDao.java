package problem1.problem1before;

public class ReportDao {
    private final String db;

    public ReportDao(String db) {
        this.db = db;
    }

    public void topStudents(int n) {
        if (db.equals("mysql")) {
            MySqlConnection c = new MySqlConnection();
            System.out.println(c.connect());
            System.out.println(new MySqlDialect().selectTop("students", n));
        } else if (db.equals("sqlserver")) {
            SqlServerConnection c = new SqlServerConnection();
            System.out.println(c.connect());
            // Original bug: a SQL Server connection is paired with a MySQL dialect.
            System.out.println(new MySqlDialect().selectTop("students", n));
        } else {
            throw new IllegalArgumentException("Unknown database: " + db);
        }
    }
}
