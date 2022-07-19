package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.RightClickMouseEvent;
import dev.rabies.machinelite.event.impl.UpdateEvent;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class InstantWither extends Module {

    private int delay, lastSlot;

    public InstantWither(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onDisabled() {
        delay = 0;
        lastSlot = 0;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof RightClickMouseEvent) {
            if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK || mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getMaterial() == Material.AIR || mc.player.inventory.getCurrentItem().getItem() != Item.getItemFromBlock(Blocks.SOUL_SAND) || delay > 0) {
                return;
            }

            BlockPos startPos = mc.objectMouseOver.getBlockPos().offset(mc.objectMouseOver.sideHit);
            EnumFacing front = mc.player.getHorizontalFacing();
            EnumFacing left = front.rotateYCCW();
            lastSlot = mc.player.inventory.currentItem;
            int[][] offset;
            byte b;
            int i;

            int sandSlot = Utils.getItemSlotByToolBar(Item.getItemFromBlock(Blocks.SOUL_SAND));
            int skullSlot = Utils.getItemSlotByToolBar(Item.getItemById(397));

            if (sandSlot != -1) {
                offset = new int[][]{new int[3], {0, 1, 0}, {1, 1, 0}, {-1, 1, 0}};
                for (i = offset.length, b = 0; b < i; ) {
                    int[] pos = offset[b];
                    this.placeBlocks(startPos.up(pos[1]).offset(front, pos[2]).offset(left, pos[0]), sandSlot);
                    b++;
                }
            }

            if (skullSlot != -1) {
                offset = new int[][]{{1, 2, 0}, {0, 2, 0}, {-1, 2, 0}};
                for (i = offset.length, b = 0; b < i; ) {
                    int[] pos = offset[b];
                    this.placeBlocks(startPos.up(pos[1]).offset(front, pos[2]).offset(left, pos[0]), skullSlot);
                    b++;
                }
            }
        }
        if (event instanceof UpdateEvent) {
            delay--;
        }
    }

    private void placeBlocks(BlockPos pos, int slot) {
        if (mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            Utils.switchItem(slot);
            Utils.placeBlock(4, pos);
            Utils.switchItem(lastSlot);
            delay = 2;
        }
    }
}
