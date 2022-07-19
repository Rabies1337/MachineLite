package dev.rabies.machinelite.module.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.module.Module;
import net.minecraft.util.text.ITextComponent;

public class AutoSign extends Module {
    private ITextComponent[] signText;

    public AutoSign(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onDisabled() {
        this.signText = null;
    }

    public ITextComponent[] getSignText() {
        return this.signText;
    }

    public void setSignText(ITextComponent[] signText) {
        this.signText = signText;
    }

    public void setSignTexts(ITextComponent[] signText) {
        if (this.isEnabled() && this.signText == null) {
            this.signText = signText;

            if (MachineLite.getModuleManager().isEnabled(Debug.class)) {
                MachineLite.WriteChat("\2477SignTextData:");
                MachineLite.WriteChat(this.signText[0]);
                MachineLite.WriteChat(this.signText[1]);
                MachineLite.WriteChat(this.signText[2]);
                MachineLite.WriteChat(this.signText[3]);
            }
        }
    }
}
