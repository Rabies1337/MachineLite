package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.command.Command;

public class ToggleCommand extends Command {
    public ToggleCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if (args == null) {
            this.printUsage();
            return;
        }
        if (args.length == 1) {
            Module module = MachineLite.getModuleManager().getModuleByString(args[0]);
            if (module != null) {
                if (module.isEnabled()) {
                    module.setDisable();
                } else {
                    module.setEnable();
                }
                MachineLite.WriteChat(String.format("\2477" + module.getName() + " was %s\2477.", module.isEnabled() ? "\247aEnabled" : "\247cDisabled"));
            } else {
                MachineLite.WriteChat("\247cModule was not found");
            }
        } else {
            this.printUsage();
        }
    }

    private void printUsage() {
        MachineLite.WriteChat("\2477.toggle <Module>");
    }
}
