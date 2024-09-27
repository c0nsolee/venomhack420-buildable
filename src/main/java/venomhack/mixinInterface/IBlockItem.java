package venomhack.mixinInterface;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;

public interface IBlockItem {
   boolean canBePlaced(ItemPlacementContext var1, BlockState var2);

   BlockState getThePlacementState(ItemPlacementContext var1);
}
