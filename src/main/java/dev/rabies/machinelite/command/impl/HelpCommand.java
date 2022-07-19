package dev.rabies.machinelite.command.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.command.Command;

import java.util.Random;

public class HelpCommand extends Command {
    private final String[] prefixes = {
            "\2475",
            "\2476",
            "\247c",
            "\247a",
            "\2479",
    };

    public HelpCommand(String[] names, String description) {
        super(names, description);
    }

    @Override
    public void fire(String[] args) {
        MachineLite.WriteChat(String.format("\2477==== %sMachineLite \247fHelp \2477====", prefixes[new Random().nextInt(prefixes.length)]));
        MachineLite.WriteChat("\2478CommandList:");
        for (Command command : MachineLite.getCommandManager().getList()) {
            MachineLite.WriteChat(String.format(" \2477.%s,", command.getNames()[0]));
        }

        MachineLite.WriteChat("");
    }
}
