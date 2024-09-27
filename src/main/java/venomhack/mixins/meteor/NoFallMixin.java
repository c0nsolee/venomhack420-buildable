package venomhack.mixins.meteor;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Send;
import meteordevelopment.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
import meteordevelopment.meteorclient.mixininterface.IPlayerMoveC2SPacket;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.Flight;
import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
import meteordevelopment.meteorclient.systems.modules.movement.NoFall.Mode;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = {NoFall.class}, remap = false)
public class NoFallMixin {
    @Shadow(remap = false)
    @Final
    private Setting<Mode> mode;

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    @EventHandler
    private void onSendPacket(Send event) {
        if (!MeteorClient.mc.player.getAbilities().creativeMode && event.packet instanceof PlayerMoveC2SPacket && this.mode.get() == Mode.Packet && ((IPlayerMoveC2SPacket) event.packet).getTag() != 1337) {
            if (!MeteorClient.mc.player.isFallFlying() && !Modules.get().isActive(Flight.class)) {
                ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
            } else if (MeteorClient.mc.player.getVelocity().y < 0.0) {
                BlockHitResult result = MeteorClient.mc.world.raycast(new RaycastContext(MeteorClient.mc.player.getPos(), MeteorClient.mc.player.getPos().subtract(0.0, 0.5, 0.0), ShapeType.OUTLINE, FluidHandling.NONE, MeteorClient.mc.player));
                if (result != null && result.getType() == Type.BLOCK) {
                    ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(true);
                }
            }
        }
    }
}
