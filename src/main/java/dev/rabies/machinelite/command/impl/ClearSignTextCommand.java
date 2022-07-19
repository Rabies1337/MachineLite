package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.command.Command;
import dev.rabies.machinelite.module.modules.AutoSign;

public class ClearSignTextCommand extends Command {

    public ClearSignTextCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if (((AutoSign) MachineLiteMod.getModuleManager().getModuleByString("AutoSign")).getSignText() != null) {
            ((AutoSign) MachineLiteMod.getModuleManager().getModuleByString("AutoSign")).setSignText(null);
            MachineLiteMod.writeChat("\2477SignText has been cleared.");
        } else {
            MachineLiteMod.writeChat("\247cAlready cleared.");
        }
    }
}
