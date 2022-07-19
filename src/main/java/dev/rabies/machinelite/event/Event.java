package dev.rabies.machinelite.event;

import dev.rabies.machinelite.MachineLiteMod;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Event {

    public boolean cancelled;
    public EventType type;

    public boolean isPre() {
        if (this.type == null)
            return false;
        return (this.type == EventType.PRE);
    }

    public boolean isPost() {
        if (this.type == null)
            return false;
        return (this.type == EventType.POST);
    }

    public Event call() {
        MachineLiteMod.getEventManager().call(this);
        return this;
    }
}
