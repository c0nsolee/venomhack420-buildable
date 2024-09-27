package venomhack.modules.chat;

import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import venomhack.Venomhack420;
import venomhack.gui.screens.GuideScreen;
import venomhack.modules.ModuleHelper;
import venomhack.utils.TextUtils;

import java.util.List;

public class AutoCope extends ModuleHelper {
    private final Setting<List<String>> messages = this.setting("messages", "The messages to send after you died.", this.sgGeneral, null, "Lag?", "I got 0 ticked");

    public AutoCope() {
        super(Venomhack420.CATEGORY, "auto-cope", "Sends a message in chat when you die.");
    }

    @EventHandler(priority = 200)
    private void onOpenScreenEvent(Receive event) {
        Packet<?> var3 = event.packet;
        if (var3 instanceof DeathMessageS2CPacket packet && packet.getEntityId() == this.mc.player.getId()) {
            TextUtils.sendNewMessage(this.messages.get());
        }
    }

    public WWidget getWidget(GuiTheme theme) {
        WVerticalList list = theme.verticalList();
        WButton placeholders = list.add(theme.button("Placeholders")).expandX().widget();
        placeholders.action = () -> new GuideScreen().show();
        return list;
    }
}
