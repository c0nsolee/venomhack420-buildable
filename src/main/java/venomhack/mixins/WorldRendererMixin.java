package venomhack.mixins;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomhack.modules.render.TanukiOutline;

@Mixin({WorldRenderer.class})
public abstract class WorldRendererMixin {
    @Shadow
    public static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j) {
    }

    @Inject(method = {"drawBlockOutline"}, at = {@At("HEAD")}, cancellable = true)
    private void onDrawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo info) {
        TanukiOutline tanukiOutline = Modules.get().get(TanukiOutline.class);
        if (tanukiOutline.isActive()) {
            Vec3d colors = tanukiOutline.getColors();
            double alpha = tanukiOutline.getAlpha();
            drawShapeOutline(matrixStack, vertexConsumer, blockState.getOutlineShape(MeteorClient.mc.world, blockPos, ShapeContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, (float) colors.x, (float) colors.y, (float) colors.z, (float) alpha);
            info.cancel();
        }
    }
}
