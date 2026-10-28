# Lab 4: Creational Patterns

Java 17 exercises for Abstract Factory, Builder and Prototype.

- `src/problem1/problem1before/`: original database code, including the mixed-dialect bug.
- `src/problem1/problem1after/`: database factories for MySQL, SQL Server and Oracle,
  including the third product, `SqlEscaper`.
- `src/problem2/problem2before/`: original laptop constructors and the first, shallow
  quote-copy implementation.
- `src/problem2/problem2after/`: immutable laptops with a builder and independent
  quote copies created from presets.
- `ANSWERS.md`: answers to every written question in the lab.
- `tests/LabChecks.java`: console checks for the expected output and the main edge cases.

Both problems belong to one project. Their before and after packages are nested
inside `problem1` and `problem2` so that the versions can be compared directly.

## Run the exercises

Open this folder in IntelliJ, select JDK 17 and run the `main` method of the
`Test` class in the package you want. The project has no external dependencies.

From a terminal in this folder, compile all exercises:

```bash
mkdir -p out
javac --release 17 -d out src/problem1/problem1before/*.java src/problem1/problem1after/*.java src/problem2/problem2before/*.java src/problem2/problem2after/*.java
```

Run the original versions to see the bugs:

```bash
java -cp out problem1.problem1before.Test
java -cp out problem2.problem2before.Test
```

Run the refactored versions:

```bash
java -cp out problem1.problem1after.Test
java -cp out problem2.problem2after.Test
```

Problem 1 prints:

```text
Connected to MySQL on port 3306
SELECT * FROM students LIMIT 3
Connected to SQL Server on port 1433
SELECT TOP 3 * FROM students
Connected to Oracle on port 1521
SELECT * FROM students FETCH FIRST 3 ROWS ONLY
```

Problem 2 prints:

```text
Laptop[cpu=i7, ram=16GB, ssd=512GB, gpu=no, backlit=yes, accessories=[Mouse]]
Invalid laptop: RAM must be between 4 and 64 GB
Quote for SAFI Amal: services=[Setup, Extended warranty]
Quote for ALAMI Samir: services=[Setup]
true
```

## Run the checks

After compiling the exercises:

```bash
javac --release 17 -cp out -d out tests/LabChecks.java
java -cp out LabChecks
```

The checks compare the four demonstrations with their expected output, exercise
all database families and their column escapers, reject unknown configuration and
preset names, check builder defaults and validation boundaries, and verify that
builder reuse and quote copying cannot change an existing laptop or another quote.
