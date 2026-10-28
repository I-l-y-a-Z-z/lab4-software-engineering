import problem1.problem1after.DatabaseFactory;
import problem2.problem2after.Laptop;
import problem2.problem2after.PresetRegistry;
import problem2.problem2after.Quote;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LabChecks {
    private static int checks;

    public static void main(String[] args) {
        checkDemonstrations();
        checkFactories();
        checkBuilder();
        checkQuotes();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void checkDemonstrations() {
        checkOutput(() -> problem1.problem1before.Test.main(new String[0]), """
                Connected to MySQL on port 3306
                SELECT * FROM students LIMIT 3
                Connected to SQL Server on port 1433
                SELECT * FROM students LIMIT 3
                """);
        checkOutput(() -> problem1.problem1after.Test.main(new String[0]), """
                Connected to MySQL on port 3306
                SELECT * FROM students LIMIT 3
                Connected to SQL Server on port 1433
                SELECT TOP 3 * FROM students
                Connected to Oracle on port 1521
                SELECT * FROM students FETCH FIRST 3 ROWS ONLY
                """);
        checkOutput(() -> problem2.problem2before.Test.main(new String[0]), """
                Laptop a: gpu=false, backlitKeyboard=true
                Invalid RAM accepted: -8
                RAM changed after ordering: 64
                Quote for SAFI Amal: services=[Setup, Extended warranty]
                Quote for ALAMI Samir: services=[Setup, Extended warranty]
                Preset after copying: Quote for : services=[Setup, Extended warranty]
                """);
        checkOutput(() -> problem2.problem2after.Test.main(new String[0]), """
                Laptop[cpu=i7, ram=16GB, ssd=512GB, gpu=no, backlit=yes, accessories=[Mouse]]
                Invalid laptop: RAM must be between 4 and 64 GB
                Quote for SAFI Amal: services=[Setup, Extended warranty]
                Quote for ALAMI Samir: services=[Setup]
                true
                """);
    }

    private static void checkFactories() {
        DatabaseFactory mysql = DatabaseFactory.forName("mysql");
        DatabaseFactory sqlserver = DatabaseFactory.forName("sqlserver");
        DatabaseFactory oracle = DatabaseFactory.forName("oracle");

        check(mysql.createDialect().selectTop("courses", 7)
                .equals("SELECT * FROM courses LIMIT 7"), "MySQL dialect");
        check(sqlserver.createDialect().selectTop("courses", 7)
                .equals("SELECT TOP 7 * FROM courses"), "SQL Server dialect");
        check(oracle.createDialect().selectTop("courses", 7)
                .equals("SELECT * FROM courses FETCH FIRST 7 ROWS ONLY"), "Oracle dialect");

        check(mysql.createEscaper().quoteColumn("name").equals("`name`"), "MySQL quoting");
        check(sqlserver.createEscaper().quoteColumn("name").equals("[name]"), "SQL Server quoting");
        check(oracle.createEscaper().quoteColumn("name").equals("\"name\""), "Oracle quoting");
        check(mysql.createEscaper().quoteColumn("a`b").equals("`a``b`"), "MySQL delimiter");
        check(sqlserver.createEscaper().quoteColumn("a]b").equals("[a]]b]"), "SQL Server delimiter");
        check(oracle.createEscaper().quoteColumn("a\"b").equals("\"a\"\"b\""), "Oracle delimiter");

        expectException(IllegalArgumentException.class, "Unknown database: postgres",
                () -> DatabaseFactory.forName("postgres"));
        expectException(IllegalArgumentException.class, "Unknown database: null",
                () -> DatabaseFactory.forName(null));
    }

    private static void checkBuilder() {
        Laptop defaults = new Laptop.Builder("i5").build();
        check(defaults.getCpu().equals("i5"), "Required CPU");
        check(defaults.getRamGb() == 8 && defaults.getSsdGb() == 256, "Default memory and storage");
        check(!defaults.hasGpu() && !defaults.hasBacklitKeyboard(), "Default options");
        check(defaults.getAccessories().isEmpty(), "Default accessories");

        Laptop minimum = new Laptop.Builder("i3").ram(4).ssd(128).build();
        check(minimum.getRamGb() == 4 && minimum.getSsdGb() == 128, "Inclusive lower limits");
        check(new Laptop.Builder("i9").ram(64).build().getRamGb() == 64, "Inclusive upper RAM limit");
        for (int ram : new int[]{-8, 3, 65}) {
            expectException(IllegalStateException.class,
                    "Invalid laptop: RAM must be between 4 and 64 GB",
                    () -> new Laptop.Builder("i5").ram(ram).build());
        }
        for (int ssd : new int[]{-1, 127}) {
            expectException(IllegalStateException.class,
                    "Invalid laptop: SSD must be at least 128 GB",
                    () -> new Laptop.Builder("i5").ssd(ssd).build());
        }
        expectException(IllegalArgumentException.class, "CPU is required", () -> new Laptop.Builder(null));
        expectException(IllegalArgumentException.class, "CPU is required", () -> new Laptop.Builder("  "));

        Laptop.Builder builder = new Laptop.Builder("i7").accessory("Mouse");
        Laptop first = builder.build();
        Laptop second = builder.ram(32).ssd(1024).gpu().backlitKeyboard().accessory("Bag").build();
        check(first.getRamGb() == 8 && first.getSsdGb() == 256, "Builder reuse preserves old numbers");
        check(!first.hasGpu() && !first.hasBacklitKeyboard(), "Builder reuse preserves old flags");
        check(first.getAccessories().equals(List.of("Mouse")), "Builder reuse preserves old accessories");
        check(second.getRamGb() == 32 && second.getSsdGb() == 1024, "Updated builder settings");
        check(second.hasGpu() && second.hasBacklitKeyboard(), "Enabled options");
        check(second.getAccessories().equals(List.of("Mouse", "Bag")), "Multiple accessories");
        expectException(UnsupportedOperationException.class, null,
                () -> first.getAccessories().add("Charger"));
        expectException(UnsupportedOperationException.class, null,
                () -> first.getAccessories().set(0, "Charger"));
    }

    private static void checkQuotes() {
        Laptop laptop = new Laptop.Builder("i5").build();
        Quote preset = new Quote(laptop).forCustomer("Preset").addService("Setup");
        PresetRegistry registry = new PresetRegistry();
        registry.register("student", preset);

        Quote q1 = registry.create("student").forCustomer("SAFI Amal");
        Quote q2 = registry.create("student").forCustomer("ALAMI Samir");
        check(q1 != q2 && q1 != preset && q2 != preset, "Distinct quote instances");
        check(q1.getLaptop() == q2.getLaptop() && q1.getLaptop() == laptop, "Shared immutable laptop");
        q1.addService("Extended warranty");
        check(q1.toString().equals("Quote for SAFI Amal: services=[Setup, Extended warranty]"),
                "First quote customization");
        check(q2.toString().equals("Quote for ALAMI Samir: services=[Setup]"), "Independent second quote");
        check(preset.toString().equals("Quote for Preset: services=[Setup]"), "Preset remains unchanged");
        check(registry.create("student").toString().equals("Quote for Preset: services=[Setup]"),
                "Future quotes use the original preset");

        Quote copied = q1.copy();
        check(copied.toString().equals(q1.toString()), "Copy preserves customer and services");
        copied.forCustomer("Another customer").addService("Delivery");
        check(q1.toString().equals("Quote for SAFI Amal: services=[Setup, Extended warranty]"),
                "Direct copy customization leaves its source unchanged");
        q1.addService("Support");
        check(copied.toString().equals("Quote for Another customer: services=[Setup, Extended warranty, Delivery]"),
                "Source customization leaves its copy unchanged");
        expectException(IllegalArgumentException.class, "Unknown preset: gaming",
                () -> registry.create("gaming"));
    }

    private static void checkOutput(Runnable action, String expected) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream captured = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            action.run();
        } finally {
            System.setOut(original);
        }
        String actual = bytes.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        check(actual.equals(expected), "Console output differs:\n" + actual);
    }

    private static void expectException(Class<? extends RuntimeException> type,
                                        String message, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            check(type.isInstance(e), "Expected " + type.getSimpleName() + ", got " + e);
            if (message != null) {
                check(message.equals(e.getMessage()), "Unexpected exception message: " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
        checks++;
    }
}
