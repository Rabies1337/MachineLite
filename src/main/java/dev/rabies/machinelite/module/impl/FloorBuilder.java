package dev.rabies.machinelite.module.impl;

import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.UpdateEvent;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.TimerUtil;
import dev.rabies.machinelite.utils.Utils;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FloorBuilder extends Module {
    private final TimerUtil timer;

    public FloorBuilder(String name, int keyCode) {
        super(name, keyCode);
        timer = new TimerUtil();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            if (!checkHeldItem()) {
                return;
            }

            Vec3d vec3d = mc.player.getPositionVector();
            BlockPos originPos = new BlockPos(vec3d.x, vec3d.y - 1, vec3d.z);
            int posX = originPos.getX();
            int posZ = originPos.getZ();
            int range = 3;

            for (int x = posX - range; x <= posX + range; x++) {
                for (int z = posZ - range; z <= posZ + range; z++) {
                    if (timer.delay(80)) {
                        final BlockPos nigger = new BlockPos(x, originPos.getY(), z);
                        this.tryToPlaceBlock(range, nigger);
                    }
                }
            }
        }
    }

    private void tryToPlaceBlock(double reach, BlockPos pos) {
        if (pos == null || !mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }

        if (Utils.placeBlock(reach, pos)) {
            timer.reset();
        }
    }

    private boolean checkHeldItem() {
        ItemStack stack = mc.player.inventory.getCurrentItem();
        return !stack.isEmpty() && (stack.getItem() instanceof ItemBlock || stack.getItem() instanceof ItemSkull);
    }
}
