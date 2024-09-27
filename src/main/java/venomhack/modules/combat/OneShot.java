package venomhack.modules.combat;

import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Send;
import meteordevelopment.meteorclient.events.packets.PacketEvent.Sent;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
import meteordevelopment.meteorclient.systems.modules.player.AntiHunger;
import meteordevelopment.meteorclient.utils.misc.Vec3;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import venomhack.Venomhack420;
import venomhack.modules.ModuleHelper;

import java.util.List;

public class OneShot extends ModuleHelper {
    public final Setting<Boolean> noRender = this.setting("no-render-arrows", "Prevents arrows from rendering.", Boolean.valueOf(true));
    private final SettingGroup sgHitmarkers = this.group("Visual");
    public final Setting<Boolean> hitMarksCrosshair = this.setting("hitmarkers", "Displays a hitmarker on your crosshair when you hit someone.", Boolean.valueOf(true), this.sgHitmarkers);
    private final Setting<List<Item>> items = this.setting("items", "What items to use the exploit on.", this.sgGeneral, false, this::itemFilter, null, null, null, Items.BOW, Items.SNOWBALL, Items.EGG, Items.ENDER_PEARL, Items.SPLASH_POTION, Items.LINGERING_POTION);
    private final Setting<Integer> timeout = this.setting("cooldown", "Milliseconds to wait between sending spoof packets.", Integer.valueOf(200), 5000.0, 20000.0);
    private final Setting<Integer> spoofs = this.setting("spoof-amount", "How many packets to send. The more packets the higher the inflicted damage.", Integer.valueOf(50), 1.0, 500.0);
    private final Setting<Boolean> bypass = this.setting("bypass", "Reverses packet order.", Boolean.valueOf(false));
    private final Setting<Boolean> rotation = this.setting("rotate", "Makes sure you face where you are looking when you shoot.", Boolean.valueOf(true));
    private final Setting<Boolean> compat = this.setting("compat", "Disables nofall and anti hunger while shooting.", Boolean.valueOf(true));
    private final Setting<Boolean> debug = this.setting("debug", "Shows debug messages.", Boolean.valueOf(false));
    private final Setting<Boolean> printDmg = this.setting("render-damage", "Sends the raw damage in chat.", Boolean.valueOf(false));
    private final Setting<Double> scale = this.setting("scale", "The scale of the text.", Double.valueOf(1.0), this.printDmg::get);
    private final Setting<Integer> markTime = this.setting("hitmarker-time", "How many ticks to render the hitmarker for after a hit.", 20, this.sgHitmarkers, 20);
    public short visibleTicks;
    private long lastShootTime;
    private boolean disabledNofall;
    private boolean disabledHunger;

    public OneShot() {
        super(Venomhack420.CATEGORY, "one-shot", "Bow exploit.");
    }

    public void onActivate() {
        this.lastShootTime = System.currentTimeMillis();
        this.disabledNofall = false;
        this.disabledHunger = false;
        this.visibleTicks = 0;
    }

    @EventHandler
    private void onPacketSend(Send event) {
        ItemStack handStack = this.mc.player.getMainHandStack();
        Packet<?> var5 = event.packet;
        if (var5 instanceof PlayerActionC2SPacket packet) {
            if (packet.getAction() == Action.RELEASE_USE_ITEM && !handStack.isEmpty() && handStack.getItem() != null && this.items.get().contains(handStack.getItem()) && (handStack.getItem() instanceof BowItem || handStack.getItem() instanceof CrossbowItem)) {
                if (System.currentTimeMillis() - this.lastShootTime < (long) this.timeout.get().intValue()) {
                    if (this.debug.get()) {
                        this.error("Cooldown not over yet... Cancelling event.");
                    }

                    event.cancel();
                    return;
                }

                if (this.debug.get()) {
                    this.info("Trying to spoof");
                }

                this.doSpoofs();
            }
        } else {
            var5 = event.packet;
            if (var5 instanceof PlayerInteractItemC2SPacket packet2 && packet2.getHand() == Hand.MAIN_HAND && !(handStack.getItem() instanceof BowItem) && !(handStack.getItem() instanceof CrossbowItem) && !handStack.isEmpty() && handStack.getItem() != null && this.items.get().contains(handStack.getItem())) {
                if (System.currentTimeMillis() - this.lastShootTime < (long) this.timeout.get().intValue()) {
                    if (this.debug.get()) {
                        this.error("Cooldown not over yet... Cancelling event.");
                    }

                    event.cancel();
                    return;
                }

                if (this.debug.get()) {
                    this.info("Trying to spoof");
                }

                this.doSpoofs();
            }
        }
    }

    @EventHandler
    private void onPacketSent(Sent event) {
        if (event.packet instanceof PlayerActionC2SPacket && ((PlayerActionC2SPacket) event.packet).getAction() == Action.RELEASE_USE_ITEM && this.items.get().contains(this.mc.player.getMainHandStack().getItem()) && this.compat.get()) {
            if (this.disabledNofall && !Modules.get().isActive(NoFall.class)) {
                Modules.get().get(NoFall.class).toggle();
                this.disabledNofall = false;
                if (this.debug.get()) {
                    this.info("Reenabled nofall");
                }
            }

            if (this.disabledHunger && !Modules.get().isActive(AntiHunger.class)) {
                Modules.get().get(AntiHunger.class).toggle();
                this.disabledHunger = false;
                if (this.debug.get()) {
                    this.info("Reenabled Anti Hunger");
                }
            }
        }
    }

    @EventHandler
    private void onPacketRecieve(Receive event) {
        Packet var3 = event.packet;
        if (var3 instanceof GameStateChangeS2CPacket packet && packet.getReason() == GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER) {
            this.visibleTicks = this.markTime.get().shortValue();
        }
    }

    @EventHandler
    private void onTick(Post event) {
        --this.visibleTicks;
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        if (this.printDmg.get()) {
            for (Entity entity : this.mc.world.getEntities()) {
                if (entity instanceof ArrowEntity) {
                    Vec3d v = entity.getVelocity();
                    if (v != null) {
                        double xx = MathHelper.lerp(event.tickDelta, entity.lastRenderX, entity.getX());
                        double yy = MathHelper.lerp(event.tickDelta, entity.lastRenderY, entity.getY());
                        double zz = MathHelper.lerp(event.tickDelta, entity.lastRenderZ, entity.getZ());
                        Vec3 pos = new Vec3().set(xx, yy, zz);
                        pos.add(0.0, (double) entity.getEyeHeight(entity.getPose()) + 0.75, 0.0);
                        double damage = MathHelper.ceil(MathHelper.clamp(v.length() * ((ArrowEntity) entity).getDamage(), 0.0, 2.147483647E9));
                        if (NametagUtils.to2D(pos, this.scale.get())) {
                            TextRenderer text = TextRenderer.get();
                            NametagUtils.begin(pos);
                            text.beginBig();
                            double w = text.getWidth(damage + "");
                            double x = -w / 2.0;
                            double y = -text.getHeight();
                            Renderer2D.COLOR.begin();
                            Renderer2D.COLOR.quad(x - 1.0, y - 1.0, w + 2.0, text.getHeight() + 2.0, new Color(0, 0, 0, 75));
                            Renderer2D.COLOR.render(null);
                            text.render(damage + "", x, y, new Color(255, 255, 255));
                            text.end();
                            NametagUtils.end();
                        }
                    }
                }
            }
        }
    }

    private void doSpoofs() {
        if (this.compat.get()) {
            if (Modules.get().isActive(NoFall.class)) {
                Modules.get().get(NoFall.class).toggle();
                this.disabledNofall = true;
                if (this.debug.get()) {
                    this.info("Turned off nofall");
                }
            }

            if (Modules.get().isActive(AntiHunger.class)) {
                Modules.get().get(AntiHunger.class).toggle();
                this.disabledHunger = true;
                if (this.debug.get()) {
                    this.info("Turned off Anti Hunger");
                }
            }
        }

        this.lastShootTime = System.currentTimeMillis();
        this.mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(this.mc.player, Mode.START_SPRINTING));

        for (int index = 0; index < this.spoofs.get(); ++index) {
            if (this.bypass.get()) {
                this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(this.mc.player.getX(), this.mc.player.getY() + 0.1, this.mc.player.getZ(), false));
                this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(this.mc.player.getX(), this.mc.player.getY() - 0.1, this.mc.player.getZ(), true));
            } else {
                this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(this.mc.player.getX(), this.mc.player.getY() - 0.1, this.mc.player.getZ(), true));
                this.mc.player.networkHandler.sendPacket(new PositionAndOnGround(this.mc.player.getX(), this.mc.player.getY() + 0.1, this.mc.player.getZ(), false));
            }

            if (this.rotation.get()) {
                this.mc.getNetworkHandler().sendPacket(new LookAndOnGround(this.mc.player.getYaw(), this.mc.player.getPitch(), this.mc.player.isOnGround()));
            }
        }

        if (this.debug.get()) {
            this.info("Spoofed");
        }
    }

    private boolean itemFilter(Item item) {
        return item instanceof BowItem || item instanceof SnowballItem || item instanceof EggItem || item instanceof EnderPearlItem || item instanceof ExperienceBottleItem || item instanceof ThrowablePotionItem;
    }
}
