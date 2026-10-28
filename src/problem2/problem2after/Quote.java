package problem2.problem2after;

import java.util.ArrayList;
import java.util.List;

public class Quote {
    private String customerName = "";
    private final Laptop laptop;
    private final List<String> services;

    public Quote(Laptop laptop) {
        this.laptop = laptop;
        services = new ArrayList<>();
    }

    private Quote(Quote src) {
        customerName = src.customerName;
        laptop = src.laptop;
        services = new ArrayList<>(src.services);
    }

    public Quote forCustomer(String name) {
        customerName = name;
        return this;
    }

    public Quote addService(String service) {
        services.add(service);
        return this;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public Quote copy() {
        return new Quote(this);
    }

    @Override
    public String toString() {
        return "Quote for " + customerName + ": services=" + services;
    }
}
