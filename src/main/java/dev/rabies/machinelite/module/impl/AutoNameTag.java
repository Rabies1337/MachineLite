package dev.rabies.machinelite.module.impl;

import dev.rabies.machinelite.MachineLite;
import dev.rabies.machinelite.event.Event;
import dev.rabies.machinelite.event.impl.UpdateEvent;
import dev.rabies.machinelite.module.Module;
import dev.rabies.machinelite.utils.Utils;
import joptsimple.internal.Strings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class AutoNameTag extends Module {
    public List<EntityLivingBase> targets;
    private EntityLivingBase target;

    public AutoNameTag(String name, int keyCode) {
        super(name, keyCode);
        this.targets = new ArrayList<>();
    }

    @Override
    public void onDisabled() {
        this.targets.clear();
        this.target = null;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            this.collectTarget();

            if (target != null) {
                int tagSlot = (mc.player.getHeldItemMainhand().getItem() == Item.getItemById(421)) ? mc.player.inventory.currentItem : Utils.getItemSlotByToolBar(Item.getItemById(421));
                int lastSlot = mc.player.inventory.currentItem;
                ItemStack currentItemStack = mc.player.inventory.getStackInSlot(tagSlot);

                if (tagSlot != -1 && this.check(target, currentItemStack.getDisplayName())) {
                    Utils.switchItem(tagSlot);

                    AxisAlignedBB boundingBox = target.getEntityBoundingBox();
                    Vec3d vec3d = new Vec3d((boundingBox.minX + boundingBox.maxX) / 2.0D, boundingBox.minY + (boundingBox.maxY - boundingBox.minY) / 100.0D * 70, (boundingBox.minZ + boundingBox.maxZ) / 2.0D);
                    float[] rotations = Utils.getNeededRotations(vec3d);
                    mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
                    EnumActionResult result = mc.playerController.interactWithEntity(mc.player, target, EnumHand.MAIN_HAND);

                    if (result == EnumActionResult.SUCCESS) {
                        MachineLite.WriteChat("Tagged " + target.getName());
                    }

                    Utils.switchItem(lastSlot);
                }
            }
        }
    }

    public void collectTarget() {
        this.targets.clear();
        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player.getDistance(entity) < 6.0F) {
                if (entity instanceof EntityWither) {
                    targets.add((EntityLivingBase) entity);
                }
            }
        }

        this.target = !targets.isEmpty() ? targets.get(0) : null;
    }

    private boolean check(Entity entity, String stackName) {
        return stackName.toLowerCase().replaceAll(" ", "").contains("ghosthax") ? !entity.getCustomNameTag().toLowerCase().replaceAll(" ", "").contains("ghosthax") : Strings.isNullOrEmpty(entity.getCustomNameTag());
    }
}
