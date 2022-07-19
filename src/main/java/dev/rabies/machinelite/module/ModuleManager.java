package dev.rabies.machinelite.module;

import dev.rabies.machinelite.module.modules.*;
import lombok.Getter;

import java.util.ArrayList;

public class ModuleManager {

    @Getter
    private final ArrayList<Module> modules = new ArrayList<>();

    public void initialize() {
        registerModule(new BuildRandom("BuildRandom", 0));
        registerModule(new InstantWither("InstantWither", 0));
        registerModule(new AntiSpam("AntiSpam", 0));
        registerModule(new AutoSign("AutoSign", 0));
        registerModule(new AutoHighway("AutoHighway", 0));
        registerModule(new AutoNameTag("AutoNameTag", 0));
        registerModule(new AntiGhostBlock("AntiGhostBlock", 0));
        registerModule(new FloorBuilder("FloorBuilder", 0));
        registerModule(new AntiMapBan("AntiMapBan", 0));

        registerModule(new Debug("Debug", 0));
    }

    private void registerModule(Module module) {
        modules.add(module);
    }

    public boolean isEnabled(Class<?> clazz) {
        Module module = this.getModuleByClass(clazz);
        return (module != null && module.isEnabled());
    }

    public Module getModuleByString(String name) {
        for (Module feature : modules) {
            if (!feature.getName().equalsIgnoreCase(name)) continue;
            return feature;
        }
        return null;
    }

    public Module getModuleByClass(Class<?> clazz) {
        for (Module feature : modules) {
            if (feature.getClass() != clazz) continue;
            return feature;
        }
        return null;
    }
}
