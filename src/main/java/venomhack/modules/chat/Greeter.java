package venomhack.modules.chat;

import com.mojang.authlib.GameProfile;
import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import venomhack.Venomhack420;
import venomhack.enums.JoinLeaveEvent;
import venomhack.events.PlayerListChangeEvent;
import venomhack.gui.screens.GuideScreen;
import venomhack.modules.ModuleHelper;
import venomhack.utils.TextUtils;

import java.util.List;

public class Greeter extends ModuleHelper {
    private final Setting<Integer> delay = this.setting("delay", "Minimum delay in ticks between sending messages.", Integer.valueOf(5), this.sgGeneral, 0.0, 20.0);
    private final Setting<Boolean> clientSide = this.setting("client-side", "Notifies you when someone joins or leaves the server.", Boolean.valueOf(false));
    private final Setting<JoinLeaveEvent> mode = this.setting("mode", "What to announce.", JoinLeaveEvent.BOTH);
    private final Setting<Boolean> onlyFriends = this.setting("only-friends", "Will only greet friends.", Boolean.valueOf(false));
    private final Setting<Boolean> dms = this.setting("dm-welcome", "Will greet the player in their dms.", Boolean.valueOf(false), () -> !this.clientSide.get() && this.mode.get().join());
    private final Setting<List<String>> welcomeMsg = this.setting("welcome-message", "Use {player} for the players name and {server.name} for the server's name.", this.sgGeneral, () -> !this.clientSide.get() && this.mode.get().join(), "Welcome {player} to {server.name}.");
    private final Setting<List<String>> byeMsg = this.setting("goodbye-message", "Use {player} for the players name and {server.name} for the server's name.", this.sgGeneral, () -> !this.clientSide.get() && this.mode.get().leave(), "See you later {player}.");
    int delayLeft;

    public Greeter() {
        super(Venomhack420.CATEGORY, "greeter", "Sends a welcome message when someone joins the server.");
    }

    public void onActivate() {
        this.delayLeft = 0;
    }

    @EventHandler
    private void onJoin(PlayerListChangeEvent.Join event) {
        if (this.welcomeMsg.get().isEmpty()) {
            this.warning(Text.translatable("greeter.noWelcome").toString());
        } else {
            PlayerListEntry player = event.getPlayer();
            GameProfile profile = player.getProfile();
            if (this.onlyFriends.get() && !Friends.get().isFriend(new OtherClientPlayerEntity(this.mc.world, profile, player.getPublicKeyData()))) {
                return;
            }

            String name = profile.getName();
            if (this.clientSide.get()) {
                ChatUtils.sendMsg(Text.literal(name).append(Text.translatable("greeter.join")).formatted(Formatting.YELLOW));
                return;
            }

            if (this.delayLeft > 0) {
                return;
            }

            StringBuilder msg = new StringBuilder();
            if (this.dms.get()) {
                msg.insert(0, "/msg " + name + " ");
            }

            msg.append(TextUtils.getNewMessage(this.welcomeMsg.get()));
            TextUtils.sendNewMessage(msg.toString().replace("{player}", name));
            this.delayLeft = this.delay.get();
        }
    }

    @EventHandler
    private void onLeave(PlayerListChangeEvent.Leave event) {
        if (this.byeMsg.get().isEmpty()) {
            this.warning(Text.translatable("greeter.noGoodbye").toString());
        } else {
            PlayerListEntry player = event.getPlayer();
            GameProfile profile = player.getProfile();
            if (this.onlyFriends.get() && !Friends.get().isFriend(new OtherClientPlayerEntity(this.mc.world, profile, player.getPublicKeyData()))) {
                return;
            }

            String name = profile.getName();
            if (this.clientSide.get()) {
                ChatUtils.sendMsg(Text.literal(name).append(Text.translatable("greeter.leave")).formatted(Formatting.YELLOW));
                return;
            }

            if (this.delayLeft > 0) {
                return;
            }

            TextUtils.sendNewMessage(TextUtils.getNewMessage(this.byeMsg.get()).replace("{player}", name));
            this.delayLeft = this.delay.get();
        }
    }

    @EventHandler
    private void onTick(Post event) {
        --this.delayLeft;
        if (this.mc.isInSingleplayer()) {
            this.error(Text.translatable("greeter.singleplayer").toString());
            this.toggle();
        }
    }

    public WWidget getWidget(GuiTheme theme) {
        WVerticalList list = theme.verticalList();
        WButton placeholders = list.add(theme.button("Placeholders")).expandX().widget();
        placeholders.action = () -> new GuideScreen().show();
        return list;
    }
}
