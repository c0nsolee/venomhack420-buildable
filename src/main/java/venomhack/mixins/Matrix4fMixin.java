package venomhack.mixins;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import venomhack.mixinInterface.IMatrix4f;

@Mixin({Matrix4f.class})
public class Matrix4fMixin implements IMatrix4f {
    @Shadow
    float m00;
    @Shadow
    float m01;
    @Shadow
    float m02;
    @Shadow
    float m03;
    @Shadow
    float m10;
    @Shadow
    float m11;
    @Shadow
    float m12;
    @Shadow
    float m13;
    @Shadow
    float m20;
    @Shadow
    float m21;
    @Shadow
    float m22;
    @Shadow
    float m23;
    @Shadow
    float m30;
    @Shadow
    float m31;
    @Shadow
    float m32;
    @Shadow
    float m33;

    @Unique
    @Override
    public void loadFromArray(float[] arr) {
        this.m00 = arr[0];
        this.m01 = arr[1];
        this.m02 = arr[2];
        this.m03 = arr[3];
        this.m10 = arr[4];
        this.m11 = arr[5];
        this.m12 = arr[6];
        this.m13 = arr[7];
        this.m20 = arr[8];
        this.m21 = arr[9];
        this.m22 = arr[10];
        this.m23 = arr[11];
        this.m30 = arr[12];
        this.m31 = arr[13];
        this.m32 = arr[14];
        this.m33 = arr[15];
    }
}
