package venomhack.mixins;

import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import venomhack.modules.render.DroppedItemsView;

@Mixin({ItemEntityRenderer.class})
public abstract class ItemEntityRendererMixin {
    @Inject(method = {"render*"}, at = {@At("HEAD")})
    private void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        DroppedItemsView dropped = Modules.get().get(DroppedItemsView.class);
        if (dropped.isActive() && !itemEntity.getStack().isEmpty()) {
            if (itemEntity.getStack().getItem() instanceof BlockItem) {
                matrixStack.multiply(new Quaternion(dropped.rotationXBlocksDropped.get().floatValue(), dropped.rotationYBlocksDropped.get().floatValue(), dropped.rotationZBlocksDropped.get().floatValue(), dropped.rotationWBlocksDropped.get().floatValue()));
                matrixStack.scale(dropped.scaleXYZBlocksDropped.get().floatValue(), dropped.scaleXYZBlocksDropped.get().floatValue(), dropped.scaleXYZBlocksDropped.get().floatValue());
            } else {
                matrixStack.multiply(new Quaternion(dropped.rotationXDropped.get().floatValue(), dropped.rotationYDropped.get().floatValue(), dropped.rotationZDropped.get().floatValue(), dropped.rotationWDropped.get().floatValue()));
                matrixStack.scale(dropped.scaleXDropped.get().floatValue(), dropped.scaleYDropped.get().floatValue(), dropped.scaleZDropped.get().floatValue());
            }
        }
    }
}
