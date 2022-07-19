package dev.rabies.machinelite.event.impl;

import dev.rabies.machinelite.event.Event;

public class RenderOverlayEvent extends Event {
    private float partialTicks;

    public RenderOverlayEvent fire(float partialTicks) {
        this.partialTicks = partialTicks;
        return this;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
