package problem1.problem1after;

public class Test {
    public static void main(String[] args) {
        String[] databases = {"mysql", "sqlserver", "oracle"};
        for (String name : databases) {
            ReportDao dao = new ReportDao(DatabaseFactory.forName(name));
            dao.topStudents(3);
        }
    }
}
