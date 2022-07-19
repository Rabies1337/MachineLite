package dev.rabies.machinelite.event.impl;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.EventType;

public class UpdateEvent extends Event {
    public UpdateEvent fire(EventType type) {
        this.type = type;
        return this;
    }
}
