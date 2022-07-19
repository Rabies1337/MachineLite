package dev.rabies.machinelite.module;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.Listener;
import dev.rabies.machinelite.utils.IMC;
import net.minecraftforge.common.MinecraftForge;

public class Module implements IMC, Listener {
    public String name;
    public int keyCode;
    public boolean enabled;

    public Module(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) {
            this.setEnable();
            MachineLite.WriteChat("\247aEnabled \2477" + this.getName());
        } else {
            this.setDisable();
            MachineLite.WriteChat("\247cDisabled \2477" + this.getName());
        }
    }

    public void setEnable() {
        this.enabled = true;
        this.onEnabled();
        MinecraftForge.EVENT_BUS.register(this);
        MachineLite.getEventManager().register(this);
        MachineLite.getProfile().saveFile();
    }

    public void setDisable() {
        this.enabled = false;
        this.onDisabled();
        MinecraftForge.EVENT_BUS.unregister(this);
        MachineLite.getEventManager().unregister(this);
        MachineLite.getProfile().saveFile();
    }

    public void onEnabled() {}

    public void onDisabled() {}

    public void onEvent(Event event) {}

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
