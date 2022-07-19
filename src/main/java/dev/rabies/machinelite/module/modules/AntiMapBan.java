package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.PacketEvent;
import dev.rabies.machinelite.event.impl.UpdateEvent;
import dev.rabies.machinelite.mixin.client.IMixinMapData;
import dev.rabies.machinelite.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

import java.util.Map;

public class AntiMapBan extends Module {

    public AntiMapBan(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            ItemStack currentItem = mc.player.inventory.getCurrentItem();
            if (!currentItem.isEmpty() && currentItem.getItem() instanceof ItemMap) {
                MapData mapData = ((ItemMap) currentItem.getItem()).getMapData(currentItem, mc.world);
                if (mapData != null)
                    getMapDecorations(mapData).clear();
            }

            mc.world.loadedEntityList.stream()
                    .filter(it -> it instanceof EntityItemFrame)
                    .forEach(it -> {
                        EntityItemFrame frame = (EntityItemFrame) it;
                        ItemStack frameItem = frame.getDisplayedItem();
                        if (!frameItem.isEmpty() && frameItem.getItem() instanceof ItemMap) {
                            MapData mapData = ((ItemMap) frameItem.getItem()).getMapData(frameItem, frame.world);
                            if (mapData != null)
                                getMapDecorations(mapData).clear();
                        }
                    });
        }
        if (event instanceof PacketEvent) {
            if (((PacketEvent) event).getPacket() instanceof SPacketMaps) {
                if (((PacketEvent) event).isIncoming())
                    event.setCancelled(true);
            }
        }
    }

    public Map<String, MapDecoration> getMapDecorations(MapData mapData) {
        if (MachineLiteMod.getModuleManager().isEnabled(Debug.class)) {
            MachineLiteMod.writeChat(String.format("MapInfo: [%s:%s:%s:%s]", mapData.mapName, mapData.scale, mapData.mapDecorations.size(), mapData.playersArrayList.size()));
        }

        return ((IMixinMapData) mapData).getMapDecorations();
    }
}
