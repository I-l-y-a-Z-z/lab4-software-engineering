package problem1.problem1before;

public class Test {
    public static void main(String[] args) {
        new ReportDao("mysql").topStudents(3);
        new ReportDao("sqlserver").topStudents(3);
    }
}
