package com.lite.machinelite.command;

import com.lite.machinelite.command.impl.BindCommand;
import com.lite.machinelite.command.impl.HelpCommand;
import com.lite.machinelite.command.impl.ToggleCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public final List<Command> commandMap = new ArrayList<>();
    public final List<Command> list = new ArrayList<>();

    public void initialize() {
        registerCommand(new BindCommand(new String[]{"bind", "b"}, ""));
        registerCommand(new ToggleCommand(new String[]{"toggle", "t"}, ""));
        registerCommand(new HelpCommand(new String[]{"help", "h"}, ""));
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
