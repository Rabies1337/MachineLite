package dev.rabies.machinelite.module.modules;

import dev.rabies.machinelite.MachineLiteMod;
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

    public List<EntityLivingBase> targets = new ArrayList<>();
    private EntityLivingBase target = null;

    public AutoNameTag(String name, int keyCode) {
        super(name, keyCode);
    }

    @Override
    public void onDisabled() {
        targets.clear();
        target = null;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof UpdateEvent) {
            collectTarget();

            target = !targets.isEmpty() ? targets.get(0) : null;
            if (target != null) return;

            int tagSlot = (mc.player.getHeldItemMainhand().getItem() == Item.getItemById(421)) ? mc.player.inventory.currentItem : Utils.getItemSlotByToolBar(Item.getItemById(421));
            if (tagSlot == -1) return;

            int lastSlot = mc.player.inventory.currentItem;
            ItemStack currentItemStack = mc.player.inventory.getStackInSlot(tagSlot);

            // "ghosthax" の部分を変更して自分の名前にすることが出来ますconfigはありませんコンパイルしてください
            if (!check0(target, currentItemStack.getDisplayName(), "ghosthax")) return;

            Utils.switchItem(tagSlot);

            AxisAlignedBB boundingBox = target.getEntityBoundingBox();
            Vec3d vec3d = new Vec3d((boundingBox.minX + boundingBox.maxX) / 2.0D, boundingBox.minY + (boundingBox.maxY - boundingBox.minY) / 100.0D * 70, (boundingBox.minZ + boundingBox.maxZ) / 2.0D);
            float[] rotations = Utils.getNeededRotations(vec3d);
            // 回転のパケットを送ってターゲットの方向を向く (eventでEntityPlayerSPを変更する方が安定するけどめんどくさいのでパス)
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));

            EnumActionResult result = mc.playerController.interactWithEntity(mc.player, target, EnumHand.MAIN_HAND);
            if (result == EnumActionResult.SUCCESS) {
                MachineLiteMod.writeChat("Tagged " + target.getName());
            }

            Utils.switchItem(lastSlot);
        }
    }

    public void collectTarget() {
        targets.clear();
        for (Entity entity : mc.world.loadedEntityList) {
            if (mc.player.getDistance(entity) < 6.0F) {
                if (!(entity instanceof EntityWither)) continue;
                targets.add((EntityLivingBase) entity);
            }
        }
    }

    // 名前チェックですこれは
    private boolean check0(Entity entity, String stackName, String target) {
        return stackName.toLowerCase().replaceAll(" ", "").contains(target.toLowerCase()) ?
                !entity.getCustomNameTag().toLowerCase().replaceAll(" ", "").contains(target.toLowerCase()) :
                Strings.isNullOrEmpty(entity.getCustomNameTag());
    }
}
