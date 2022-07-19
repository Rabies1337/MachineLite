package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.event.impl.RenderOverlayEvent;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {
    @Inject(method = "renderHotbar", at = @At("RETURN"))
    private void renderHotbar(ScaledResolution sr, float partialTicks, CallbackInfo callbackInfo) {
        RenderOverlayEvent renderOverlayEvent = new RenderOverlayEvent();
        renderOverlayEvent.fire(partialTicks).call();
    }
}
