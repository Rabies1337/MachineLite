package dev.rabies.machinelite.command;

import com.lite.machinelite.command.impl.*;
import dev.rabies.machinelite.command.impl.*;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public final List<Command> commandMap = new ArrayList<>();
    public final List<Command> list = new ArrayList<>();

    public void initialize() {
        registerCommand(new BindCommand(new String[]{"bind", "b"}, "Binds a module to a specified key."));
        registerCommand(new ToggleCommand(new String[]{"toggle", "t"}, "Toggles the module."));
        registerCommand(new HelpCommand(new String[]{"help", "h"}, "Show help."));
        registerCommand(new ModuleListCommand(new String[]{"modulelist", "ml"}, "Check Modules"));
        registerCommand(new ClearSignTextCommand(new String[]{"clearsigntext", "clearsign", "cst"}, "Clears the AutoSign text."));
    }

    public void registerCommand(Command command) {
        list.add(command);

        int i = (command.getNames()).length;
        for (int j = 0; j < i; j++) {
            commandMap.add(command);
        }
    }

    public Command getCommand(String name) {
        for (Command command : commandMap) {
            for (String usage : command.getNames()) {
                if (usage.equalsIgnoreCase(name)) {
                    return command;
                }
            }
        }
        return null;
    }

    public List<Command> getList() {
        return list;
    }
}
