package dev.rabies.machinelite.module;

import com.lite.machinelite.module.impl.*;
import dev.rabies.machinelite.module.impl.*;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<>();
    }

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

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByString(String name) {
        try {
            for (Module feature : this.modules) {
                if (feature.getName().toLowerCase().replaceAll(" ", "").equals(name.toLowerCase())) {
                    return feature;
                }
            }
        } catch (Exception exception) {
            return null;
        }
        return null;
    }

    public Module getModuleByClass(Class<?> clazz) {
        try {
            for (Module feature : this.modules) {
                if (feature.getClass() == clazz) {
                    return feature;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
