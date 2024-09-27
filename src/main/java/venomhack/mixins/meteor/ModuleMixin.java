package venomhack.mixins.meteor;

import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import venomhack.mixinInterface.IBlink;

@Mixin(value = Module.class, remap = false)
public abstract class ModuleMixin implements IBlink {
    @Unique
    private Vec3d oldPos;

    @Unique
    @Override
    public Vec3d getOldPos() {
        return this.oldPos;
    }
}
