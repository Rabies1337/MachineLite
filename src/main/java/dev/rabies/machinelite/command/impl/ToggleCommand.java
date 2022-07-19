package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.command.Command;

public class ToggleCommand extends Command {

    public ToggleCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if (args == null || args.length != 1) {
            MachineLiteMod.writeChat("\2477.toggle <Module>");
            return;
        }

        Module module = MachineLiteMod.getModuleManager().getModuleByString(args[0]);
        if (module == null) {
            MachineLiteMod.writeChat("\247cModule was not found");
            return;
        }

        module.toggle();
        MachineLiteMod.writeChat(String.format("\2477" + module.getName() + " was %s\2477.", module.isEnabled() ? "\247aEnabled" : "\247cDisabled"));
    }
}
