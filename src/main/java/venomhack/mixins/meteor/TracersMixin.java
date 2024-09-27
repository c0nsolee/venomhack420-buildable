package venomhack.mixins.meteor;

import meteordevelopment.meteorclient.settings.BoolSetting.Builder;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.render.Tracers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Tracers.class}, remap = false)
public class TracersMixin {
    @Shadow(remap = false)
    @Final
    private SettingGroup sgGeneral;
    @Unique
    private Setting<Boolean> friends = null;

    @Inject(method = {"<init>"}, at = {@At("TAIL")}, remap = false)
    private void onInit(CallbackInfo ci) {
        this.friends = this.sgGeneral.add(new Builder().name("show-friends").description("Whether to draw tracers to friends or not.").defaultValue(false).build());
    }
}
