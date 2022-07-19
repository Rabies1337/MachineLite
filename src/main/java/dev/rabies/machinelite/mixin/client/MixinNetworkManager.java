package dev.rabies.machinelite.mixin.client;

import dev.rabies.machinelite.event.impl.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void SendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent();
        packetEvent.preFire(packet).call();

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void ChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent packetEvent = new PacketEvent();
        packetEvent.postFire(packet).call();

        if (packetEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
