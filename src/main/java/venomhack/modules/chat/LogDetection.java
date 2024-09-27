package venomhack.modules.chat;

import meteordevelopment.meteorclient.events.world.TickEvent.Post;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.orbit.EventHandler;
import venomhack.Venomhack420;
import venomhack.events.PlayerListChangeEvent;
import venomhack.gui.screens.GuideScreen;
import venomhack.modules.ModuleHelper;
import venomhack.utils.TextUtils;

import java.util.List;

public class LogDetection extends ModuleHelper {
    private final SettingGroup sgMessages = this.settings.createGroup("Log Messages");
    private final Setting<Integer> delay = this.setting("delay", "Minimum ticks between sending messages.", Integer.valueOf(5), this.sgGeneral, 0.0, 20.0);
    private final Setting<List<String>> messages = this.setting("", "A random message will be chosen to make fun of your victims.", this.sgMessages, null, "LMAO {player} just logged. Venomhack owns me and all!");
    private int delayLeft;

    public LogDetection() {
        super(Venomhack420.CATEGORY, "log-detection", "Sends a chat message when someone combat logs.");
    }

    public void onActivate() {
        this.delayLeft = 0;
    }

    @EventHandler
    private void onLeave(PlayerListChangeEvent.Leave event) {
        if (this.delayLeft <= 0 && Venomhack420.STATS.isTarget(event.getPlayer())) {
            if (this.messages.get().isEmpty()) {
                this.warning("You have no messages set.");
                return;
            }

            TextUtils.sendNewMessage(TextUtils.getNewMessage(this.messages.get()).replace("{player}", event.getPlayer().getProfile().getName()));
            this.delayLeft = this.delay.get();
        }
    }

    @EventHandler
    private void onPostTick(Post event) {
        --this.delayLeft;
    }

    public WWidget getWidget(GuiTheme theme) {
        WVerticalList list = theme.verticalList();
        WButton placeholders = list.add(theme.button("Placeholders")).expandX().widget();
        placeholders.action = () -> new GuideScreen().show();
        return list;
    }
}
