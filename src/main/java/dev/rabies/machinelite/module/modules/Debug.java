package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.module.Module;

public class Debug extends Module {

    public Debug(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onEvent(Event event) {
        // Nothing
    }
}
