package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.module.modules.AutoSign;
import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.utils.IMC;
import net.minecraft.client.Minecraft;
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

    @Shadow @Final
    private TileEntitySign tileSign;

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci) {
        AutoSign autoSign = (AutoSign) MachineLiteMod.getModuleManager().getModuleByString("AutoSign");
        if (autoSign == null || !autoSign.isEnabled()) return;

        ITextComponent[] signText = autoSign.getSignText();
        if (signText == null) return;

        tileSign.signText[0] = signText[0];
        tileSign.signText[1] = signText[1];
        tileSign.signText[2] = signText[2];
        tileSign.signText[3] = signText[3];
        Minecraft.getMinecraft().displayGuiScreen(null);
    }

    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/tileentity/TileEntitySign;markDirty()V"))
    protected void actionPerformed(GuiButton button, CallbackInfo ci) {
        AutoSign autoSign = (AutoSign) MachineLiteMod.getModuleManager().getModuleByString("AutoSign");
        if (autoSign == null || !autoSign.isEnabled()) return;
        if (tileSign.signText == null) {
            MachineLiteMod.writeChat("No text available");
            return;
        }

        autoSign.setSignTexts(tileSign.signText);
        MachineLiteMod.writeChat("Set SignText");
    }
}
