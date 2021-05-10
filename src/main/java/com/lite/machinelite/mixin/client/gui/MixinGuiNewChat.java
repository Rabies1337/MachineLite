package com.lite.machinelite.mixin.client.gui;

import com.google.common.collect.Lists;
import com.lite.machinelite.event.impl.ChatInputEvent;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui {
    @Final
    @Shadow
    private final List<ChatLine> drawnChatLines = Lists.<ChatLine>newArrayList();

    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"), cancellable = true)
    public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId, CallbackInfo ci) {
        ChatInputEvent chatInputEvent = new ChatInputEvent(chatComponent, drawnChatLines);
        chatInputEvent.call();

        if (chatInputEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
