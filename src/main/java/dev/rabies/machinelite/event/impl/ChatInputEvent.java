package dev.rabies.machinelite.event.impl;

import dev.rabies.machinelite.event.Event;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ChatInputEvent extends Event {
    private ITextComponent textComponent;
    private List<ChatLine> chatLines;

    public ChatInputEvent(ITextComponent textComponent, List<ChatLine> chatLines) {
        this.textComponent = textComponent;
        this.chatLines = chatLines;
    }

    public ITextComponent getTextComponent() {
        return textComponent;
    }

    public List<ChatLine> getChatLines() {
        return chatLines;
    }
}
