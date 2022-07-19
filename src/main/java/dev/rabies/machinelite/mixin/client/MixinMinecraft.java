package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.event.impl.RightClickMouseEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "rightClickMouse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I"), cancellable = true)
    private void rightClickMouse(CallbackInfo ci) {
        RightClickMouseEvent rightClickMouseEvent = new RightClickMouseEvent();
        rightClickMouseEvent.call();

        if (rightClickMouseEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
