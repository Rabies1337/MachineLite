package dev.rabies.machinelite.module;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.event.EventListener;
import dev.rabies.machinelite.utils.IMC;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.common.MinecraftForge;

public abstract class Module implements IMC, EventListener {

    @Getter @Setter
    public String name;
    @Getter @Setter
    public int keyCode;
    @Getter
    public boolean enabled;

    public Module(String name, int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            setEnable();
            MachineLiteMod.writeChat("\247aEnabled \2477" + getName());
        } else {
            setDisable();
            MachineLiteMod.writeChat("\247cDisabled \2477" + getName());
        }
    }

    public void setEnable() {
        onEnabled();
        MinecraftForge.EVENT_BUS.register(this);
        MachineLiteMod.getEventManager().register(this);
        MachineLiteMod.getConfig().saveConfig();
    }

    public void setDisable() {
        onDisabled();
        MinecraftForge.EVENT_BUS.unregister(this);
        MachineLiteMod.getEventManager().unregister(this);
        MachineLiteMod.getConfig().saveConfig();
    }

    public void onEnabled() {
        enabled = true;
    }

    public void onDisabled() {
        enabled = false;
    }
}
