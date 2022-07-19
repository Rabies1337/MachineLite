package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.command.Command;
import dev.rabies.machinelite.module.Module;
import org.lwjgl.input.Keyboard;

public class ModuleListCommand extends Command {
    public ModuleListCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        MachineLite.WriteChat("\2477==== \2475MachineLite \247fModules \2477====");
        for (Module module : MachineLite.getModuleManager().getModules()) {
            MachineLite.WriteChat(((module.getKeyCode() != 0) ? "\2477Name: " + (module.isEnabled() ? "\247a" : "\247c") + module.getName() + "\2477, Key: \2479" + Keyboard.getKeyName(module.getKeyCode()) : "\2477Name: " + (module.isEnabled() ? "\247a" : "\247c") + module.getName()) + "\2477,");
        }

        MachineLite.WriteChat("");
    }
}
