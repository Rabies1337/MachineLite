package dev.rabies.machinelite.command;

import dev.rabies.machinelite.command.impl.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    @Getter
    public final List<Command> commandMap = new ArrayList<>();
    @Getter
    public final List<Command> commands = new ArrayList<>();

    public void initialize() {
        registerCommand(new BindCommand(new String[]{"bind", "b"}, "Binds a module to a specified key."));
        registerCommand(new ToggleCommand(new String[]{"toggle", "t"}, "Toggles the module."));
        registerCommand(new HelpCommand(new String[]{"help", "h"}, "Show help."));
        registerCommand(new ModuleListCommand(new String[]{"modulelist", "ml"}, "Check Modules"));
        registerCommand(new ClearSignTextCommand(new String[]{"clearsigntext", "clearsign", "cst"}, "Clears the AutoSign text."));
    }

    public void registerCommand(Command command) {
        commands.add(command);
        Arrays.stream(command.getNames()).forEach(it -> commandMap.add(command));
    }

    public Command getCommand(String name) {
        for (Command command : commandMap) {
            for (String usage : command.getNames()) {
                if (!usage.equalsIgnoreCase(name)) continue;
                return command;
            }
        }
        return null;
    }
}
