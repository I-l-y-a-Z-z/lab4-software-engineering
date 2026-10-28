package problem1.problem1after;

public class OracleEscaper implements SqlEscaper {
    @Override
    public String quoteColumn(String column) {
        return "\"" + column.replace("\"", "\"\"") + "\"";
    }
}
