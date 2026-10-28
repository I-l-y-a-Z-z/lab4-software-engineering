package problem2.problem2after;

import java.util.ArrayList;
import java.util.List;

public final class Laptop {
    private final String cpu;
    private final int ramGb;
    private final int ssdGb;
    private final boolean gpu;
    private final boolean backlitKeyboard;
    private final List<String> accessories;

    private Laptop(Builder builder) {
        cpu = builder.cpu;
        ramGb = builder.ramGb;
        ssdGb = builder.ssdGb;
        gpu = builder.gpu;
        backlitKeyboard = builder.backlitKeyboard;
        accessories = List.copyOf(builder.accessories);
    }

    public String getCpu() {
        return cpu;
    }

    public int getRamGb() {
        return ramGb;
    }

    public int getSsdGb() {
        return ssdGb;
    }

    public boolean hasGpu() {
        return gpu;
    }

    public boolean hasBacklitKeyboard() {
        return backlitKeyboard;
    }

    public List<String> getAccessories() {
        return accessories;
    }

    @Override
    public String toString() {
        return "Laptop[cpu=" + cpu + ", ram=" + ramGb + "GB, ssd=" + ssdGb
                + "GB, gpu=" + (gpu ? "yes" : "no")
                + ", backlit=" + (backlitKeyboard ? "yes" : "no")
                + ", accessories=" + accessories + "]";
    }

    public static class Builder {
        private final String cpu;
        private int ramGb = 8;
        private int ssdGb = 256;
        private boolean gpu;
        private boolean backlitKeyboard;
        private final List<String> accessories = new ArrayList<>();

        public Builder(String cpu) {
            if (cpu == null || cpu.isBlank()) {
                throw new IllegalArgumentException("CPU is required");
            }
            this.cpu = cpu;
        }

        public Builder ram(int ramGb) {
            this.ramGb = ramGb;
            return this;
        }

        public Builder ssd(int ssdGb) {
            this.ssdGb = ssdGb;
            return this;
        }

        public Builder gpu() {
            gpu = true;
            return this;
        }

        public Builder backlitKeyboard() {
            backlitKeyboard = true;
            return this;
        }

        public Builder accessory(String accessory) {
            accessories.add(accessory);
            return this;
        }

        public Laptop build() {
            if (ramGb < 4 || ramGb > 64) {
                throw new IllegalStateException("Invalid laptop: RAM must be between 4 and 64 GB");
            }
            if (ssdGb < 128) {
                throw new IllegalStateException("Invalid laptop: SSD must be at least 128 GB");
            }
            return new Laptop(this);
        }
    }
}
