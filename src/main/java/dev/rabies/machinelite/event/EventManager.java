package dev.rabies.machinelite.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager {
    protected List<EventListener> contents = new CopyOnWriteArrayList<>();

    public void call(Event event) {
        if (contents == null) return;
        for (EventListener listener : contents) {
            if (listener == null) continue;
            listener.onEvent(event);
        }
    }

    public void register(EventListener eventListener) {
        if (contents.contains(eventListener)) return;
        contents.add(eventListener);
    }

    public void unregister(EventListener eventListener) {
        if (!contents.contains(eventListener)) return;
        contents.remove(eventListener);
    }
}
