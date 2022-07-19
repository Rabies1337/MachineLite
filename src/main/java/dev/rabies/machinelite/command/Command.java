package dev.rabies.machinelite.command;

import dev.rabies.machinelite.utils.IMC;
import lombok.Getter;

@Getter
public class Command implements Fire, IMC {

    private final String[] names;
    private final String description;

    public Command(String[] names, String description) {
        this.names = names;
        this.description = description;
    }

    public void fire(String[] args) {
    }
}
