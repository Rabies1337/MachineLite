package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.module.Module;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.text.ITextComponent;

public class AutoSign extends Module {

    @Getter @Setter
    private ITextComponent[] signText;

    public AutoSign(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onDisabled() {
        signText = null;
    }

    public void setSignTexts(ITextComponent[] signText) {
        if (signText == null) return;
        this.signText = signText;

        if (MachineLiteMod.getModuleManager().isEnabled(Debug.class)) {
            MachineLiteMod.writeChat("\2477SignTextData:");
            MachineLiteMod.writeChat(this.signText[0]);
            MachineLiteMod.writeChat(this.signText[1]);
            MachineLiteMod.writeChat(this.signText[2]);
            MachineLiteMod.writeChat(this.signText[3]);
        }
    }

    @Override
    public void onEvent(Event event) {
        // Nothing
    }
}
