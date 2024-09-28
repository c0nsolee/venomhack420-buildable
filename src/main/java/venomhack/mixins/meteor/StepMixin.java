package venomhack.mixins.meteor;

import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.Step;
import meteordevelopment.meteorclient.systems.modules.movement.Step.ActiveWhen;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomhack.modules.movement.Anchor;

import static meteordevelopment.meteorclient.MeteorClient.mc;

@Mixin(value = {Step.class}, remap = false)
public abstract class StepMixin {
    @Shadow(remap = false)
    @Final
    public Setting<Double> height;
    @Shadow(remap = false)
    @Final
    private SettingGroup sgGeneral;
    @Shadow(remap = false)
    @Final
    private Setting<ActiveWhen> activeWhen;
    @Shadow(remap = false)
    @Final
    private Setting<Boolean> safeStep;
    @Shadow(remap = false)
    @Final
    private Setting<Integer> stepHealth;
    @Unique
    private Setting<Boolean> ncp = null;

    @Shadow(remap = false)
    public abstract void onDeactivate();

    @Inject(method = {"<init>"}, at = {@At("TAIL")}, remap = false)
    private void onInit(CallbackInfo ci) {
        this.ncp = this.sgGeneral.add(new Builder().name("ncp-mode").description("Attempts to bypass strict no-cheat-plus servers.").defaultValue(false).build());
    }

    @Inject(method = {"onTick"}, at = {@At("HEAD")}, remap = false, cancellable = true)
    private void onTick(Post event, CallbackInfo ci) {
        if (Modules.get().get(Anchor.class).cancelStep()) {
            this.onDeactivate();
            ci.cancel();
        } else if (this.ncp.get()) {
            this.doNcpStep();
            ci.cancel();
        }
    }

    @Unique
    private void doNcpStep() {
        assert mc.player != null;
        mc.player.setStepHeight(0.6F);
        switch (this.activeWhen.get()) {
            case Sneaking -> {
                if (!mc.player.isSneaking()) {
                    return;
                }
            }
            case NotSneaking -> {
                if (mc.player.isSneaking()) {
                    return;
                }
            }
        }

        if (!this.safeStep.get() || !(PlayerUtils.getTotalHealth() <= (double) this.stepHealth.get()) && !(PlayerUtils.getTotalHealth() - PlayerUtils.possibleHealthReductions() <= (double) this.stepHealth.get().intValue())) {
            mc.player.setStepHeight(this.height.get().floatValue());
            if (mc.player.horizontalCollision && mc.player.isOnGround() && !mc.player.isHoldingOntoLadder() && !mc.options.jumpKey.isPressed() && mc.player.fallDistance == 0.0F && (mc.player.forwardSpeed != 0.0F || mc.player.sidewaysSpeed != 0.0F)) {
                for (double i = 1.0; i <= this.height.get(); i += 0.5) {
                    Box box = mc.player.getBoundingBox().offset(0.0, i, 0.0);
                    assert mc.world != null;
                    if (mc.world.isSpaceEmpty(box.expand(0.05, 0.0, 0.0)) && mc.world.isSpaceEmpty(box.expand(0.0, 0.0, 0.05))) {
                        if (i == 1.0 && this.height.get() == 1.0) {
                            this.sendPacket(0.41999998688698);
                            this.sendPacket(0.7531999805212);
                            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 1.0, mc.player.getZ());
                        }

                        if (i == 1.5) {
                            this.sendPacket(0.41999998688698);
                            this.sendPacket(0.7531999805212);
                            this.sendPacket(1.00133597911214);
                            this.sendPacket(1.16610926093821);
                            this.sendPacket(1.24918707874468);
                            this.sendPacket(1.1707870772188);
                            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 1.0, mc.player.getZ());
                        }

                        if (i == 2.0) {
                            this.sendPacket(0.42);
                            this.sendPacket(0.78);
                            this.sendPacket(0.63);
                            this.sendPacket(0.51);
                            this.sendPacket(0.9);
                            this.sendPacket(1.21);
                            this.sendPacket(1.45);
                            this.sendPacket(1.43);
                            mc.player.updatePosition(mc.player.getX(), mc.player.getY() + 2.0, mc.player.getZ());
                        }
                        break;
                    }
                }
            }
        }
    }

    @Unique
    private void sendPacket(double y) {
        mc.player.networkHandler.sendPacket(new PositionAndOnGround(mc.player.getX(), mc.player.getY() + y, mc.player.getZ(), mc.player.isOnGround()));
    }
}
