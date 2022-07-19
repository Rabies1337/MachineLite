package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.module.impl.AutoSign;
import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.utils.IMC;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiEditSign.class)
public class MixinGuiEditSign {
    @Shadow
    @Final
    private TileEntitySign tileSign;

    @Inject(method = "initGui", at = @At("HEAD"), cancellable = true)
    public void initGui(CallbackInfo ci) {
        ITextComponent[] signText = ((AutoSign) MachineLite.getModuleManager().getModuleByString("AutoSign")).getSignText();
        if (signText != null) {
            this.tileSign.signText[0] = signText[0];
            this.tileSign.signText[1] = signText[1];
            this.tileSign.signText[2] = signText[2];
            this.tileSign.signText[3] = signText[3];
            IMC.mc.displayGuiScreen(null);
        }
    }

    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntitySign;markDirty()V"))
    protected void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (MachineLite.getModuleManager().getModuleByString("AutoSign").isEnabled()) {
            ((AutoSign) MachineLite.getModuleManager().getModuleByString("AutoSign")).setSignTexts(this.tileSign.signText);
            MachineLite.WriteChat("Set SignText");
        }
    }
}
