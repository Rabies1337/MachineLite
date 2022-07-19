package dev.rabies.machinelite.event;

import dev.rabies.machinelite.MachineLite;

public abstract class Event {
    public boolean cancelled;

    public EventType type;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

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
        MachineLite.getEventManager().call(this);
        return this;
    }
}