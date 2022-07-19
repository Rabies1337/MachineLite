package dev.rabies.machinelite;

import dev.rabies.machinelite.command.Command;
import dev.rabies.machinelite.command.CommandManager;
import dev.rabies.machinelite.config.Config;
import dev.rabies.machinelite.event.EventManager;
import dev.rabies.machinelite.module.ModuleManager;
import dev.rabies.machinelite.utils.IMC;
import lombok.Getter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

@Mod(
        modid = MachineLiteMod.CLIENT_ID,
        name = MachineLiteMod.CLIENT_NAME,
        version = MachineLiteMod.CLIENT_VERSION
)
public class MachineLiteMod implements IMC {

    public final static String CLIENT_ID = "machinelite";
    public final static String CLIENT_NAME = "MachineLite";
    public final static String CLIENT_VERSION = "v1.7";
    private boolean helpNotifier;

    @Getter
    private static EventManager eventManager;
    @Getter
    private static ModuleManager moduleManager;
    @Getter
    private static CommandManager commandManager;
    @Getter
    private static Config config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) throws Exception {
        MinecraftForge.EVENT_BUS.register(this);

        moduleManager.initialize();
        commandManager.initialize();
        config.loadConfig();

        helpNotifier = true;
    }

    @SubscribeEvent
    public void keyEvent(InputEvent.KeyInputEvent e) {
        if (mc.currentScreen != null) return;
        if (!Keyboard.isCreated()) return;
        if (!Keyboard.getEventKeyState()) return;
        int keyCode = Keyboard.getEventKey();
        if (keyCode != 0) {
            moduleManager.getModules().forEach(module -> {
                if (keyCode == module.getKeyCode()) {
                    module.toggle();
                }
            });
        }
    }

    @SubscribeEvent
    public void onWorldEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityPlayerSP && helpNotifier) {
            Command command = commandManager.getCommand("help");
            command.fire(null);
            helpNotifier = false;
        }
    }

    @SubscribeEvent
    public void chatEvent(ClientChatEvent event) {
        String message = event.getMessage();
        if (!message.startsWith(".")) return;

        String[] bits = message.substring(".".length()).split(" ");
        Command command = commandManager.getCommand(bits[0]);
        if (command == null) {
            MachineLiteMod.writeChat("\247cInvalid Command");
            return;
        }

        if (bits.length > 1) {
            String[] args = Arrays.copyOfRange(bits, 1, bits.length);
            command.fire(args);
        } else {
            command.fire(null);
        }

        event.setCanceled(true);
    }

    public static void writeChat(Object message) {
        mc.ingameGUI.addChatMessage(ChatType.SYSTEM, new TextComponentString("\2477[\2475Machine \247fLite\2477] \247f" + message.toString()));
    }
}
