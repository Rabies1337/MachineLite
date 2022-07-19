package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.command.Command;
import dev.rabies.machinelite.module.impl.AutoSign;

public class ClearSignTextCommand extends Command {
    public ClearSignTextCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        if (((AutoSign) MachineLite.getModuleManager().getModuleByString("AutoSign")).getSignText() != null) {
            ((AutoSign) MachineLite.getModuleManager().getModuleByString("AutoSign")).setSignText(null);
            MachineLite.WriteChat("\2477SignText has been cleared.");
        } else {
            MachineLite.WriteChat("\247cAlready cleared.");
        }
    }
}
