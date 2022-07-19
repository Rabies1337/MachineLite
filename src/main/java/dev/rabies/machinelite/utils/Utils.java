package dev.rabies.machinelite.utils;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.module.impl.AntiGhostBlock;
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

public class Utils implements IMC {
    public static void switchItem(int slot) {
        if (mc.player.inventory.currentItem != slot) {
            mc.player.inventory.currentItem = slot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.playerController.updateController();
        }
    }

    public static int getItemSlotByToolBar(Item itemIn) {
        for (byte slot = 0; slot < 9; slot++) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(slot);
            if (itemStack.getItem() == itemIn) {
                return slot;
            }
        }

        return -1;
    }

    public static EnumFacing getFacing() {
        switch (MathHelper.floor((double) (mc.player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7) {
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
                    float[] rotations = Utils.getNeededRotations(hitVec);
                    RayTraceResult traceResult = Utils.rayTraceBlocks(reach, rotations[0], rotations[1]);

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

                        if (MachineLite.getModuleManager().isEnabled(AntiGhostBlock.class)) {
                            mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(neighbor, facing.getOpposite(), EnumHand.MAIN_HAND, 0, 0, 0));
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static float[] getNeededRotations(Vec3d vec) {
        Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = mc.player.rotationYaw + MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F - mc.player.rotationYaw);
        float pitch = mc.player.rotationPitch + MathHelper.wrapDegrees((float) -Math.toDegrees(Math.atan2(diffY, diffXZ)) - mc.player.rotationPitch);
        return new float[]{yaw, pitch};
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }

    public static RayTraceResult rayTraceBlocks(double reach, float yaw, float pitch) {
        Vec3d vec3 = mc.player.getPositionEyes(1.0f);
        Vec3d vec4 = Utils.getVectorForRotation(pitch, yaw);
        Vec3d vec5 = vec3.add(vec4.x * reach, vec4.y * reach, vec4.z * reach);
        return mc.player.world.rayTraceBlocks(vec3, vec5, false, false, true);
    }

    public static boolean isBlockContainer(Block block) {
        return block instanceof BlockContainer || block instanceof BlockWorkbench || block instanceof BlockAnvil;
    }
}
