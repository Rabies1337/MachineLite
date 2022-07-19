package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.UpdateEvent;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class AutoHighway extends Module {

    private final ArrayList<BlockPos> positions = new ArrayList<>();

    public AutoHighway(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onEnabled() {
        positions.clear();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            if (event.isPre()) return;
            if (!mc.player.onGround) return;
            if (mc.player.inventory.getCurrentItem().isEmpty()) return;
            if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock)) return;

            final Vec3d playerPos = mc.player.getPositionVector();
            BlockPos originPos = new BlockPos(playerPos.x, playerPos.y + 0.5f, playerPos.z);

            if (positions.isEmpty()) {
                switch (Utils.getFacing()) {
                    case EAST:
                        positions.add(originPos.down());
                        positions.add(originPos.down().east());
                        positions.add(originPos.down().east().north());
                        positions.add(originPos.down().east().south());
                        positions.add(originPos.down().east().north().north());
                        positions.add(originPos.down().east().south().south());
                        positions.add(originPos.down().east().north().north().north());
                        positions.add(originPos.down().east().south().south().south());
                        positions.add(originPos.down().east().north().north().north().up());
                        positions.add(originPos.down().east().south().south().south().up());
                        break;
                    case NORTH:
                        positions.add(originPos.down());
                        positions.add(originPos.down().north());
                        positions.add(originPos.down().north().east());
                        positions.add(originPos.down().north().west());
                        positions.add(originPos.down().north().east().east());
                        positions.add(originPos.down().north().west().west());
                        positions.add(originPos.down().north().east().east().east());
                        positions.add(originPos.down().north().west().west().west());
                        positions.add(originPos.down().north().east().east().east().up());
                        positions.add(originPos.down().north().west().west().west().up());
                        break;
                    case SOUTH:
                        positions.add(originPos.down());
                        positions.add(originPos.down().south());
                        positions.add(originPos.down().south().east());
                        positions.add(originPos.down().south().west());
                        positions.add(originPos.down().south().east().east());
                        positions.add(originPos.down().south().west().west());
                        positions.add(originPos.down().south().east().east().east());
                        positions.add(originPos.down().south().west().west().west());
                        positions.add(originPos.down().south().east().east().east().up());
                        positions.add(originPos.down().south().west().west().west().up());
                        break;
                    case WEST:
                        positions.add(originPos.down());
                        positions.add(originPos.down().west());
                        positions.add(originPos.down().west().north());
                        positions.add(originPos.down().west().south());
                        positions.add(originPos.down().west().north().north());
                        positions.add(originPos.down().west().south().south());
                        positions.add(originPos.down().west().north().north().north());
                        positions.add(originPos.down().west().south().south().south());
                        positions.add(originPos.down().west().north().north().north().up());
                        positions.add(originPos.down().west().south().south().south().up());
                        break;
                }
            }

            if (positions.size() <= 64) {
                for (BlockPos pos : positions) {
                    if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) continue;
                    Utils.placeBlock(4, pos);
                }
                positions.clear();
            }
        }
    }
}
