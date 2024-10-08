package venomhack.modules.render;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent.Pre;
import meteordevelopment.meteorclient.renderer.Renderer3D;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.world.BlockIterator;
import meteordevelopment.meteorclient.utils.world.Dir;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import venomhack.modules.ModuleHelper;

import java.util.ArrayList;
import java.util.List;

public class HoleEsp extends ModuleHelper {
    private final SettingGroup sgRender = this.group("Render");
    private final Setting<Integer> horizontalRadius = this.setting("horizontal-radius", "Horizontal radius in which to search for holes.", Integer.valueOf(10), 1.0, 32.0);
    private final Setting<Integer> verticalRadius = this.setting("vertical-radius", "Vertical radius in which to search for holes.", Integer.valueOf(5), 1.0, 10.0);
    private final Setting<Integer> holeHeight = this.setting("min-height", "Minimum hole height required to be rendered.", Integer.valueOf(3), 1.0, 5.0);
    private final Setting<Boolean> doubles = this.setting("doubles", "Highlights double holes that can be stood across.", Boolean.valueOf(true));
    private final Setting<Boolean> ignoreOwn = this.setting("ignore-own", "Ignores rendering the hole you are currently standing in.", Boolean.valueOf(false));
    private final Setting<Boolean> webs = this.setting("webs", "Whether to show holes that have webs inside of them.", Boolean.valueOf(false));
    private final Setting<Double> height = this.setting("height", "The height of rendering.", Double.valueOf(1.0), 0.0, 5.0);
    private final Setting<ShapeMode> topMode = this.setting("top-shape-mode", "How the shapes are rendered for the top part.", ShapeMode.Both);
    private final Setting<ShapeMode> bottomMode = this.setting("bottom-shape-mode", "How the shapes are rendered for the bottom part.", ShapeMode.Both);
    private final Setting<Boolean> topQuad = this.setting("top-quad", "Whether to render a quad at the top of the hole.", Boolean.valueOf(false), () -> this.topMode.get().sides());
    private final Setting<Boolean> bottomQuad = this.setting("bottom-quad", "Whether to render a quad at the bottom of the hole.", Boolean.valueOf(true), () -> this.bottomMode.get().sides());
    private final Setting<SettingColor> bedrockTopSide = this.setting("bedrock-top-side", "The top side color for holes that are completely bedrock.", 100, 255, 0, 0, this.sgRender, () -> this.topMode.get().sides());
    private final Setting<SettingColor> bedrockTopLine = this.setting("bedrock-top-line", "The top line color for holes that are completely bedrock.", 100, 255, 0, 128, this.sgRender, () -> this.topMode.get().lines());
    private final Setting<SettingColor> bedrockBottomSide = this.setting("bedrock-bottom-side", "The bottom side color for holes that are completely bedrock.", 100, 255, 0, 100, this.sgRender, () -> this.bottomMode.get().sides());
    private final Setting<SettingColor> bedrockBottomLine = this.setting("bedrock-bottom-line", "The bottom line color for holes that are completely bedrock.", 100, 255, 0, 255, this.sgRender, () -> this.bottomMode.get().lines());
    private final Setting<SettingColor> obbyTopSide = this.setting("obsidian-top-side", "The top color side for holes that are not completely bedrock.", 255, 0, 0, 0, this.sgRender, () -> this.topMode.get().sides());
    private final Setting<SettingColor> obbyTopLine = this.setting("obsidian-top-line", "The top color line for holes that are not completely bedrock.", 255, 0, 0, 128, this.sgRender, () -> this.topMode.get().lines());
    private final Setting<SettingColor> obbyBottomSide = this.setting("obsidian-bottom-side", "The bottom side color for holes that are not completely bedrock.", 255, 0, 0, 100, this.sgRender, () -> this.bottomMode.get().sides());
    private final Setting<SettingColor> obbyBottomLine = this.setting("obsidian-bottom-line", "The bottom line color for holes that are not completely bedrock.", 255, 0, 0, 255, this.sgRender, () -> this.bottomMode.get().lines());
    private final Pool<Hole> holePool = new Pool(Hole::new);
    private final List<Hole> holes = new ArrayList<>();

    public HoleEsp() {
        super(Categories.Render, "hole-esp", "Displays holes that you will take less damage in.");
    }

    @EventHandler
    private void onTick(Pre event) {
        for (Hole hole : this.holes) {
            this.holePool.free(hole);
        }

        this.holes.clear();
        BlockIterator.register(this.horizontalRadius.get(), this.verticalRadius.get(), (blockPos, blockState) -> {
            if (this.validHole(blockPos)) {
                int bedrock = 0;
                int obsidian = 0;
                Direction air = null;

                for (Direction direction : Direction.values()) {
                    if (direction != Direction.UP) {
                        BlockState state = this.mc.world.getBlockState(blockPos.offset(direction));
                        if (state.getBlock() == Blocks.BEDROCK) {
                            ++bedrock;
                        } else if (state.getBlock().getBlastResistance() >= 600.0F) {
                            ++obsidian;
                        } else {
                            if (direction == Direction.DOWN) {
                                return;
                            }

                            if (this.validHole(blockPos.offset(direction)) && air == null) {
                                for (Direction dir : Direction.values()) {
                                    if (dir != direction.getOpposite() && dir != Direction.UP) {
                                        BlockState blockState1 = this.mc.world.getBlockState(blockPos.offset(direction).offset(dir));
                                        if (blockState1.getBlock() == Blocks.BEDROCK) {
                                            ++bedrock;
                                        } else {
                                            if (!(blockState1.getBlock().getBlastResistance() >= 600.0F)) {
                                                return;
                                            }

                                            ++obsidian;
                                        }
                                    }
                                }

                                air = direction;
                            }
                        }
                    }
                }

                if (obsidian + bedrock == 5 && air == null) {
                    this.holes.add(this.holePool.get().set(blockPos, bedrock == 5 ? Hole.Type.Bedrock : Hole.Type.Obsidian, (byte) 0));
                } else if (obsidian + bedrock == 8 && this.doubles.get() && air != null) {
                    this.holes.add(this.holePool.get().set(blockPos, bedrock == 8 ? Hole.Type.Bedrock : Hole.Type.Obsidian, Dir.get(air)));
                }
            }
        });
    }

    private boolean validHole(BlockPos pos) {
        if (this.ignoreOwn.get() && this.mc.player.getBlockPos().equals(pos)) {
            return false;
        } else if (!this.webs.get() && this.mc.world.getBlockState(pos).getBlock() == Blocks.COBWEB) {
            return false;
        } else if (!this.mc.world.getBlockState(pos).getOutlineShape(this.mc.world, pos).isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < this.holeHeight.get(); ++i) {
                if (!this.mc.world.getBlockState(pos.up(i)).getOutlineShape(this.mc.world, pos.up(i)).isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        for (Hole hole : this.holes) {
            hole.render(event.renderer);
        }
    }

    private static class Hole {
        public Mutable blockPos = new Mutable();
        public byte exclude;
        public Type type;

        public Hole set(BlockPos blockPos, Type type, byte exclude) {
            this.blockPos.set(blockPos);
            this.exclude = exclude;
            this.type = type;
            return this;
        }

        public Color getColor(boolean bottom, boolean line) {
            HoleEsp hesp = Modules.get().get(HoleEsp.class);
            if (this.type == Type.Bedrock) {
                if (bottom) {
                    if (line) {
                        return hesp.bottomMode.get().lines() ? hesp.bedrockBottomLine.get() : new Color(0, 0, 0, 0);
                    } else {
                        return hesp.bottomMode.get().sides() ? hesp.bedrockBottomSide.get() : new Color(0, 0, 0, 0);
                    }
                } else if (line) {
                    return hesp.topMode.get().lines() ? hesp.bedrockTopLine.get() : new Color(0, 0, 0, 0);
                } else {
                    return hesp.topMode.get().sides() ? hesp.bedrockTopSide.get() : new Color(0, 0, 0, 0);
                }
            } else if (bottom) {
                if (line) {
                    return hesp.bottomMode.get().lines() ? hesp.obbyBottomLine.get() : new Color(0, 0, 0, 0);
                } else {
                    return hesp.bottomMode.get().sides() ? hesp.obbyBottomSide.get() : new Color(0, 0, 0, 0);
                }
            } else if (line) {
                return hesp.topMode.get().lines() ? hesp.obbyTopLine.get() : new Color(0, 0, 0, 0);
            } else {
                return hesp.topMode.get().sides() ? hesp.obbyTopSide.get() : new Color(0, 0, 0, 0);
            }
        }

        public void render(Renderer3D renderer) {
            HoleEsp hesp = Modules.get().get(HoleEsp.class);
            double height = hesp.height.get();
            int x = this.blockPos.getX();
            int y = this.blockPos.getY();
            int z = this.blockPos.getZ();
            if (hesp.topMode.get().lines() || hesp.topMode.get().lines()) {
                if (Dir.isNot(this.exclude, (byte) 32) && Dir.isNot(this.exclude, (byte) 8)) {
                    renderer.line(x, y, z, x, (double) y + height, z, this.getColor(true, true), this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 32) && Dir.isNot(this.exclude, (byte) 16)) {
                    renderer.line(x, y, z + 1, x, (double) y + height, z + 1, this.getColor(true, true), this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 64) && Dir.isNot(this.exclude, (byte) 8)) {
                    renderer.line(x + 1, y, z, x + 1, (double) y + height, z, this.getColor(true, true), this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 64) && Dir.isNot(this.exclude, (byte) 16)) {
                    renderer.line(x + 1, y, z + 1, x + 1, (double) y + height, z + 1, this.getColor(true, true), this.getColor(false, true));
                }
            }

            if (hesp.bottomMode.get().lines()) {
                if (Dir.isNot(this.exclude, (byte) 8)) {
                    renderer.line(x, y, z, x + 1, y, z, this.getColor(true, true));
                }

                if (Dir.isNot(this.exclude, (byte) 16)) {
                    renderer.line(x, y, z + 1, x + 1, y, z + 1, this.getColor(true, true));
                }

                if (Dir.isNot(this.exclude, (byte) 32)) {
                    renderer.line(x, y, z, x, y, z + 1, this.getColor(true, true));
                }

                if (Dir.isNot(this.exclude, (byte) 64)) {
                    renderer.line(x + 1, y, z, x + 1, y, z + 1, this.getColor(true, true));
                }
            }

            if (hesp.topMode.get().lines()) {
                if (Dir.isNot(this.exclude, (byte) 8)) {
                    renderer.line(x, (double) y + height, z, x + 1, (double) y + height, z, this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 16)) {
                    renderer.line(x, (double) y + height, z + 1, x + 1, (double) y + height, z + 1, this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 32)) {
                    renderer.line(x, (double) y + height, z, x, (double) y + height, z + 1, this.getColor(false, true));
                }

                if (Dir.isNot(this.exclude, (byte) 64)) {
                    renderer.line(x + 1, (double) y + height, z, x + 1, (double) y + height, z + 1, this.getColor(false, true));
                }
            }

            if (hesp.topMode.get().sides() || hesp.bottomMode.get().sides()) {
                if (Dir.isNot(this.exclude, (byte) 8)) {
                    renderer.gradientQuadVertical(x, y, z, x + 1, (double) y + height, z, this.getColor(false, false), this.getColor(true, false));
                }

                if (Dir.isNot(this.exclude, (byte) 32)) {
                    renderer.gradientQuadVertical(x, y, z, x, (double) y + height, z + 1, this.getColor(false, false), this.getColor(true, false));
                }

                if (Dir.isNot(this.exclude, (byte) 16)) {
                    renderer.gradientQuadVertical(x, y, z + 1, x + 1, (double) y + height, z + 1, this.getColor(false, false), this.getColor(true, false));
                }

                if (Dir.isNot(this.exclude, (byte) 64)) {
                    renderer.gradientQuadVertical(x + 1, y, z, x + 1, (double) y + height, z + 1, this.getColor(false, false), this.getColor(true, false));
                }
            }

            if (hesp.bottomMode.get().sides() && Dir.isNot(this.exclude, (byte) 4) && hesp.bottomQuad.get()) {
                renderer.quad(x, y, z, x, y, z + 1, x + 1, y, z + 1, x + 1, y, z, this.getColor(true, false));
            }

            if (hesp.topMode.get().sides() && Dir.isNot(this.exclude, (byte) 2) && hesp.topQuad.get()) {
                renderer.quad(x, (double) y + height, z, x, (double) y + height, z + 1, x + 1, (double) y + height, z + 1, x + 1, (double) y + height, z, this.getColor(false, false));
            }
        }

        public enum Type {
            Bedrock, Obsidian, Mixed
        }
    }
}
