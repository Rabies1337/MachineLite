package dev.rabies.machinelite.module.impl;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.ChatInputEvent;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.IMC;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class AntiSpam extends Module {
    public AntiSpam(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof ChatInputEvent) {
            List<ChatLine> chatLines = ((ChatInputEvent) event).getChatLines();
            if (!chatLines.isEmpty()) {
                final GuiNewChat chat = IMC.mc.ingameGUI.getChatGUI();
                final int maxTextLength = MathHelper.floor(chat.getChatWidth() / chat.getChatScale());
                final List<ITextComponent> newLines = GuiUtilRenderComponents.splitText(((ChatInputEvent) event).getTextComponent(), maxTextLength, IMC.mc.fontRenderer, false, false);
                int spamCounter = 1;
                int matchingLines = 0;
                for (int i = chatLines.size() - 1; i >= 0; --i) {
                    final String oldLine = chatLines.get(i).getChatComponent().getUnformattedText();
                    if (matchingLines <= newLines.size() - 1) {
                        final String newLine = newLines.get(matchingLines).getUnformattedText();
                        if (matchingLines < newLines.size() - 1) {
                            if (oldLine.equals(newLine)) {
                                ++matchingLines;
                                continue;
                            }
                            matchingLines = 0;
                            continue;
                        } else {
                            if (!oldLine.startsWith(newLine)) {
                                matchingLines = 0;
                                continue;
                            }
                            if (i > 0 && matchingLines == newLines.size() - 1) {
                                final String twoLines = oldLine + chatLines.get(i - 1).getChatComponent().getUnformattedText();
                                final String addedText = twoLines.substring(newLine.length());
                                if (addedText.startsWith(" [x") && addedText.endsWith("]")) {
                                    final String oldSpamCounter = addedText.substring(3, addedText.length() - 1);
                                    if (isInteger(oldSpamCounter)) {
                                        spamCounter += Integer.parseInt(oldSpamCounter);
                                        ++matchingLines;
                                        continue;
                                    }
                                }
                            }
                            if (oldLine.length() == newLine.length()) {
                                ++spamCounter;
                            } else {
                                final String addedText2 = oldLine.substring(newLine.length());
                                if (!addedText2.startsWith(" [x") || !addedText2.endsWith("]")) {
                                    matchingLines = 0;
                                    continue;
                                }
                                final String oldSpamCounter2 = addedText2.substring(3, addedText2.length() - 1);
                                if (!isInteger(oldSpamCounter2)) {
                                    matchingLines = 0;
                                    continue;
                                }
                                spamCounter += Integer.parseInt(oldSpamCounter2);
                            }
                        }
                    }
                    if (i + matchingLines >= i) {
                        chatLines.subList(i, i + matchingLines + 1).clear();
                    }
                    matchingLines = 0;
                }
                if (spamCounter > 1) {
                    ((ChatInputEvent) event).getTextComponent().appendText(" [x" + spamCounter + "]");
                }
            }
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }
}
