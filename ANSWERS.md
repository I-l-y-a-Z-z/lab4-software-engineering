Lab 4 - Creational Patterns

Problem 1 - Abstract Factory

1) Both queries use LIMIT 3, even the SQL Server one. The SQL Server branch uses MySqlDialect by mistake. SQL Server would give a syntax error near LIMIT because it needs SELECT TOP 3 * FROM students. Java does not catch this because the query is just a String. It does not check SQL syntax or which database it belongs to.

2) The repeated if/else breaks DRY. Adding another database means changing the DAO methods, which goes against OCP. The DAO creates concrete classes directly instead of depending on interfaces, which goes against DIP. It also handles both object creation and reporting, so it has more than one responsibility (SRP). Choosing the connection and dialect separately makes it easy to mix them up.

3) I added DbConnection with connect() and SqlDialect with selectTop(String table, int n). The connection and dialect classes implement these interfaces.

4) DatabaseFactory has createConnection() and createDialect(). MySqlFactory returns the MySQL products and SqlServerFactory returns the SQL Server products.

5) ReportDao gets a DatabaseFactory in its constructor and uses it to create both products. It has no if, switch or new. The DAO cannot mix the families itself anymore because the same factory supplies both objects. The factories still have to be written correctly.

6) I added OracleConnection.java, OracleDialect.java and OracleFactory.java. Oracle uses port 1521 and the query is SELECT * FROM students FETCH FIRST 3 ROWS ONLY. I updated Test.java to try Oracle. ReportDao did not need changes. For question 7, I also added the oracle case in DatabaseFactory.java.

7) forName() chooses the factory using the database name. The switch is fine here because it is in one place, instead of repeating it in every DAO method. Unknown names throw IllegalArgumentException.

8) Four existing files need changes: DatabaseFactory.java, MySqlFactory.java, SqlServerFactory.java and OracleFactory.java. They all need createEscaper(). I also added SqlEscaper.java and one escaper class per database. MySQL uses backticks, SQL Server uses square brackets and Oracle uses double quotes. This shows that adding a new family is easy, but adding a new product type means changing every factory.

9) createConnection() alone only chooses the connection. The DAO could still use the wrong dialect. Abstract Factory creates the related products together through the same factory.

Problem 2 - Builder and Prototype

Part A

1) false means no GPU and true means a backlit keyboard, but we cannot tell that just by looking at the call. There are too many constructor arguments and overloads. The public fields can be changed after ordering, and there is no validation for things like negative RAM.

2) Both constructors would have the signature Laptop(String, int). Java uses the parameter types to tell constructors apart, not their names, so ramGb and ssdGb do not make them different.

3) I made a static Builder inside Laptop. CPU is required. The defaults are 8 GB RAM, 256 GB SSD, no GPU, no backlit keyboard and no accessories. Each optional method returns the builder. build() throws IllegalStateException if RAM is outside 4 to 64 GB or SSD is below 128 GB. Laptop has a private constructor and private final fields. List.copyOf keeps the accessories list from being changed.

4) I built the i7 laptop with ram(16), ssd(512), backlitKeyboard() and accessory("Mouse"). It prints:

Laptop[cpu=i7, ram=16GB, ssd=512GB, gpu=no, backlit=yes, accessories=[Mouse]]

Building the i5 with -8 GB RAM throws an exception and prints:

Invalid laptop: RAM must be between 4 and 64 GB

Part B

5) Quote stores the customer name, laptop and services. forCustomer() and addService() return this. copy() uses a copy constructor. In the first version, services = src.services makes both quotes use the same list. I kept this version in problem2before.

6) PresetRegistry stores the presets by name and create() returns a copy. An unknown name throws IllegalArgumentException. With the shallow copy, both Amal and Samir get Extended warranty, and even the preset changes. This happens because all three quotes share the services list. A deep copy of the list gives each quote its own list.

7) I fixed it with services = new ArrayList<>(src.services). Now only Amal gets Extended warranty and Samir only gets Setup. The laptop comparison prints true. Sharing the laptop is safe because it is immutable, so nobody can change it. The services list can change, so each quote needs its own copy. The strings in the list can still be shared because strings are immutable.

8) A Singleton would let everyone use the same preset catalogue. But it would also make tests share state and make separate catalogues harder to use. I kept it as a normal class and can pass the same instance around when needed.

9)

a) Builder, because the URL is required and the other settings can be added with named methods.

b) Prototype, because the shape can copy itself without the editor knowing its concrete class.

c) Singleton, because the application needs one shared connection pool.

d) Factory Method, because each application can override the method that creates its Document.

e) Abstract Factory, because it creates a matching family of desktop or mobile widgets.
