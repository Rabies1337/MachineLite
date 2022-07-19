package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.command.Command;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand(String[] names, String description) {
        super(names, description);
    }

    public void fire(String[] args) {
        if (args != null && args.length > 0) {
            Module module = MachineLite.getModuleManager().getModuleByString(args[0]);

                if (module != null) {
                    if (args.length == 1) {
                        MachineLite.WriteChat(String.format("\2477The current key for \247b%s \2477is \2479%s.", module.getName(), Keyboard.getKeyName(module.getKeyCode())));
                    } else {
                        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                        module.setKeyCode(key);
                        MachineLite.WriteChat("\2477" + module.getName() + " bind to \247f" + Keyboard.getKeyName(module.getKeyCode()) + "\2477.");
                    }
                } else {
                    MachineLite.WriteChat("\247cModule was not found");
                }
        } else {
            MachineLite.WriteChat("\2477.bind <Module> <Key>");
        }
    }
}
