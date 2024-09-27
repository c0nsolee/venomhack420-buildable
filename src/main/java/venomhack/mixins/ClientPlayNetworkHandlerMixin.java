package venomhack.mixins;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import venomhack.modules.render.BetterPops;

@Mixin({ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
    @Unique
    private static ItemStack getActiveTotemOfUndying(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                return itemStack;
            }
        }

        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    /**
     * @author
     * @reason
     */

    @Overwrite
    public void onEntityStatus(EntityStatusS2CPacket packet) {
        BetterPops betterPops = Modules.get().get(BetterPops.class);
        NetworkThreadUtils.forceMainThread(packet, MinecraftClient.getInstance().getNetworkHandler(), MeteorClient.mc);
        Entity entity = packet.getEntity(MeteorClient.mc.world);
        if (entity != null) {
            if (packet.getStatus() == 21) {
                MeteorClient.mc.getSoundManager().play(new GuardianAttackSoundInstance((GuardianEntity) entity));
            } else if (packet.getStatus() == 35) {
                if (betterPops.isActive()) {
                    boolean isSelf = entity == MeteorClient.mc.player;
                    int maxAge = 30;
                    if (betterPops.shouldSpawnExtra(isSelf)) {
                        maxAge = isSelf ? betterPops.aliveTicksSelf.get() : betterPops.aliveTicksOther.get();
                    }

                    for (int i = 0; i < (betterPops.shouldSpawnExtra(isSelf) ? isSelf ? betterPops.extraAmountSelf.get() : betterPops.extraAmountOther.get() : 1); ++i) {
                        MeteorClient.mc.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, maxAge);
                    }
                } else {
                    MeteorClient.mc.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                }

                MeteorClient.mc.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F, false);
                if (entity == MeteorClient.mc.player) {
                    MeteorClient.mc.gameRenderer.showFloatingItem(getActiveTotemOfUndying(MeteorClient.mc.player));
                }
            } else {
                entity.handleStatus(packet.getStatus());
            }
        }
    }
}
