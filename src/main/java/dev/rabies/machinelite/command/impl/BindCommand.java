package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.command.Command;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {

    public BindCommand(String[] names, String description) {
        super(names, description);
    }

    public void fire(String[] args) {
        if (args == null || args.length == 0) {
            MachineLiteMod.writeChat("\2477.bind <Module> <Key>");
            return;
        }

        Module module = MachineLiteMod.getModuleManager().getModuleByString(args[0]);
        if (module == null) {
            MachineLiteMod.writeChat("\247cModule was not found");
            return;
        }

        if (args.length == 1) {
            MachineLiteMod.writeChat(String.format("\2477The current key for \247b%s \2477is \2479%s.", module.getName(), Keyboard.getKeyName(module.getKeyCode())));
        } else {
            int key = Keyboard.getKeyIndex(args[1].toUpperCase());
            module.setKeyCode(key);
            MachineLiteMod.writeChat("\2477" + module.getName() + " bind to \247f" + Keyboard.getKeyName(module.getKeyCode()) + "\2477.");
        }
    }
}
