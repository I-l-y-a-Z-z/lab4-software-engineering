package problem2.problem2before;

public class Test {
    public static void main(String[] args) {
        Laptop a = new Laptop("i7", 16, 512, false, true);
        System.out.println("Laptop a: gpu=" + a.gpu + ", backlitKeyboard=" + a.backlitKeyboard);

        Laptop b = new Laptop("i5", -8);
        System.out.println("Invalid RAM accepted: " + b.ramGb);
        b.ramGb = 64;
        System.out.println("RAM changed after ordering: " + b.ramGb);

        PresetRegistry registry = new PresetRegistry();
        Quote student = new Quote(new Laptop("i5", 8)).addService("Setup");
        registry.register("student", student);

        Quote q1 = registry.create("student")
                .forCustomer("SAFI Amal")
                .addService("Extended warranty");
        Quote q2 = registry.create("student").forCustomer("ALAMI Samir");
        System.out.println(q1);
        System.out.println(q2);
        System.out.println("Preset after copying: " + student);
    }
}
