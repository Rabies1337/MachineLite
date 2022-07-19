package dev.rabies.machinelite.event;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    protected List<Listener> contents;

    public void initialize() {
        this.contents = new CopyOnWriteArrayList<>();
    }

    public void call(Event event) {
        if (this.contents != null)
            try {
                for (Listener listener : this.contents) {
                    if (listener != null) {
                        listener.onEvent(event);
                    }
                }
            } catch (ConcurrentModificationException | java.util.NoSuchElementException var4) {
                var4.printStackTrace();
            }
    }

    public void register(Listener listener) {
        if (!this.contents.contains(listener)) {
            this.contents.add(listener);
        }
    }

    public void unregister(Listener listener) {
        if (this.contents.contains(listener)) {
            this.contents.remove(listener);
        }
    }
}
