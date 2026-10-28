package problem1.problem1after;

public class SqlServerEscaper implements SqlEscaper {
    @Override
    public String quoteColumn(String column) {
        return "[" + column.replace("]", "]]") + "]";
    }
}
