package venomhack.utils;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class PathUtils extends Utils {
    private int maxLength;
    private BlockState placeState;

    public PathUtils(int maxLength, BlockState placeState) {
        this.maxLength = maxLength;
        this.placeState = placeState;
    }

    public static void smartAdd(Set<BlockPos> set, BlockPos pos) {
        set.add(pos);
    }

    public static void smartAdd(List<BlockPos> list, BlockPos pos) {
        if (!list.contains(pos)) {
            list.add(pos);
        }
    }

    public static void smartAdd(List<PathBlock> list, PathBlock pos) {
        if (!list.contains(pos)) {
            list.add(pos);
        }
    }

    public void setPlaceState(BlockState placeState) {
        this.placeState = placeState;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    private PathResult findPath(List<PathBlock> list, PathBlock pos, int iteration, @Nullable List<PathBlock> shortestPath) {
        if (iteration <= this.maxLength && (shortestPath == null || iteration <= shortestPath.size())) {
            for (Direction direction : Direction.values()) {
                if (MeteorClient.mc.world.canPlace(this.placeState, pos.offset(direction).getBlockPos(), ShapeContext.absent())) {
                    if (list.contains(pos.offset(direction))) {
                        return new PathResult(list, pos.setPredictDirection(direction.getOpposite()));
                    }

                    if (!MeteorClient.mc.world.getBlockState(pos.offset(direction).getBlockPos()).getMaterial().isReplaceable()) {
                        return new PathResult(list, pos);
                    }
                }
            }

            for (Direction direction : Direction.values()) {
                if (MeteorClient.mc.world.canPlace(this.placeState, pos.offset(direction).getBlockPos(), ShapeContext.absent())) {
                    PathResult pathResult = this.findPath(list, pos.offset(direction), iteration + 1, null);
                    if (pathResult != null && !pathResult.list.isEmpty()) {
                        smartAdd(list, pos);
                        return new PathResult(list, pos);
                    }
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static class PathBlock {
        private final BlockPos pos;
        private final int priority;
        private Direction predictDirection = null;

        public PathBlock(BlockPos pos, int priority) {
            this.pos = pos;
            this.priority = priority;
        }

        public PathBlock(BlockPos pos, int priority, Direction predictDirection) {
            this.pos = pos;
            this.priority = priority;
            this.predictDirection = predictDirection;
        }

        public PathBlock offset(Direction direction) {
            return new PathBlock(this.pos.offset(direction), this.priority);
        }

        public BlockPos getBlockPos() {
            return this.pos;
        }

        public int getPriority() {
            return this.priority;
        }

        public Direction getPredictDirection() {
            return this.predictDirection;
        }

        public PathBlock setPredictDirection(Direction predictDirection) {
            return new PathBlock(this.pos, this.priority, predictDirection);
        }
    }

    public static class PathResult {
        private final List<PathBlock> list;
        private final PathBlock pos;

        public PathResult(List<PathBlock> list, PathBlock pos) {
            this.list = list;
            this.pos = pos;
        }

        public List<PathBlock> getList() {
            return this.list;
        }

        public PathBlock getPos() {
            return this.pos;
        }
    }
}
