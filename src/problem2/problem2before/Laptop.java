package problem2.problem2before;

import java.util.ArrayList;
import java.util.List;

public class Laptop {
    public String cpu;
    public int ramGb, ssdGb;
    public boolean gpu, backlitKeyboard;
    public List<String> accessories = new ArrayList<>();

    public Laptop(String cpu, int ramGb, int ssdGb,
                  boolean gpu, boolean backlitKeyboard) {
        this.cpu = cpu;
        this.ramGb = ramGb;
        this.ssdGb = ssdGb;
        this.gpu = gpu;
        this.backlitKeyboard = backlitKeyboard;
    }

    public Laptop(String cpu, int ramGb) {
        this(cpu, ramGb, 256, false, false);
    }

    public Laptop(String cpu, int ramGb, int ssdGb) {
        this(cpu, ramGb, ssdGb, false, false);
    }
}
