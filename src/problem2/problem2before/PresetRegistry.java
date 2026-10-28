package problem2.problem2before;

import java.util.HashMap;
import java.util.Map;

public class PresetRegistry {
    private final Map<String, Quote> presets = new HashMap<>();

    public void register(String name, Quote preset) {
        presets.put(name, preset);
    }

    public Quote create(String name) {
        Quote preset = presets.get(name);
        if (preset == null) {
            throw new IllegalArgumentException("Unknown preset: " + name);
        }
        return preset.copy();
    }
}
