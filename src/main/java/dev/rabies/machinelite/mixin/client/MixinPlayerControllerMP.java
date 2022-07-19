package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.event.impl.EventBreakBlock;
import dev.rabies.machinelite.utils.IMC;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "onPlayerDestroyBlock", at = @At("RETURN"))
    public void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        EventBreakBlock eventBreakBlock = new EventBreakBlock();
        eventBreakBlock.fire(pos, IMC.mc.world.getBlockState(pos)).call();
    }
}