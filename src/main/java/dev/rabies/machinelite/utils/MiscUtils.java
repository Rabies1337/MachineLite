package dev.rabies.machinelite.utils;

import dev.rabies.machinelite.MachineLiteMod;
import dev.rabies.machinelite.module.modules.AntiGhostBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class MiscUtils implements IMC {

    public static void switchItem(int slot) {
        if (mc.player.inventory.currentItem != slot) {
            mc.player.inventory.currentItem = slot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.playerController.updateController();
        }
    }

    public static int getItemSlotByToolBar(Item itemIn) {
        for (int slot = 0; slot < 9; slot++) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(slot);
            if (itemStack.getItem() != itemIn) continue;
            return slot;
        }
        return -1;
    }

    public static EnumFacing getFacing() {
        switch (MathHelper.floor((mc.player.rotationYaw * 8.0 / 360.0) + 0.5) & 7) {
            case 0:
            case 1:
                return EnumFacing.SOUTH;
            case 2:
            case 3:
                return EnumFacing.WEST;
            case 4:
            case 5:
            default:
                return EnumFacing.NORTH;
            case 6:
            case 7:
                return EnumFacing.EAST;
        }
    }

    public static boolean placeBlock(double reach, BlockPos pos) {
        Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        final Vec3d posVec = new Vec3d(pos).add(0.5, 0.5, 0.5);

        for (EnumFacing facing : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(facing);

            if (mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(pos), false)) {
                final Vec3d dirVec = new Vec3d(facing.getDirectionVec());
                final Vec3d hitVec = posVec.add(dirVec.scale(0.5));
                if (eyesPos.squareDistanceTo(hitVec) <= Math.pow(6.0, 2.0)) {
                    float[] rotations = RotationUtil.getNeededRotations(hitVec);
                    RayTraceResult traceResult = MiscUtils.rayTraceBlocks(reach, rotations[0], rotations[1]);

                    if (traceResult.sideHit != null && traceResult.hitVec != null && traceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                        if (isBlockContainer(mc.world.getBlockState(neighbor).getBlock())) {
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        }

//                        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
                        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, facing.getOpposite(), hitVec, EnumHand.MAIN_HAND);
                        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));

                        if (isBlockContainer(mc.world.getBlockState(neighbor).getBlock())) {
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }

                        if (MachineLiteMod.getModuleManager().isEnabled(AntiGhostBlock.class)) {
                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(neighbor, facing.getOpposite(), EnumHand.MAIN_HAND, 0, 0, 0));
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static RayTraceResult rayTraceBlocks(double reach, float yaw, float pitch) {
        Vec3d vec3 = mc.player.getPositionEyes(1.0f);
        Vec3d vec4 = RotationUtil.getVectorForRotation(pitch, yaw);
        Vec3d vec5 = vec3.add(vec4.x * reach, vec4.y * reach, vec4.z * reach);
        return mc.player.world.rayTraceBlocks(vec3, vec5, false, false, true);
    }

    public static boolean isBlockContainer(Block block) {
        return block instanceof BlockContainer || block instanceof BlockWorkbench || block instanceof BlockAnvil;
    }
}
