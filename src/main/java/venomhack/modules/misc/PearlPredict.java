package venomhack.modules.misc;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.utils.misc.Pool;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.*;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Vector3d;
import venomhack.Venomhack420;
import venomhack.modules.ModuleHelper;

import java.util.ArrayList;
import java.util.List;

public class PearlPredict extends ModuleHelper {
    private final Setting<ShapeMode> shapeMode = this.setting("shape-mode", "How the shapes are rendered.", ShapeMode.Both);
    private final Setting<SettingColor> sideColor = this.setting("side-color", "The side color.", 255, 150, 0, 35, this.sgGeneral);
    private final Setting<SettingColor> lineColor = this.setting("line-color", "The line color.", 255, 150, 0, this.sgGeneral);
    private final Pool<Vector3d> vec3s = new Pool(Vector3d::new);
    private final List<Path> paths = new ArrayList<>();

    public PearlPredict() {
        super(Venomhack420.CATEGORY, "pearl-predict", "Predicts accurately where ender pearls will land.");
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        for (Path path : this.paths) {
            path.clear();
        }

        for (Entity entity : this.mc.world.getEntities()) {
            if (entity instanceof EnderPearlEntity) {
                this.getEmptyPath().calculate((EnderPearlEntity) entity);
            }
        }

        for (Path path : this.paths) {
            path.render(event);
        }
    }

    private Path getEmptyPath() {
        for (Path path : this.paths) {
            if (path.points.isEmpty()) {
                return path;
            }
        }

        Path path = new Path();
        this.paths.add(path);
        return path;
    }

    private boolean isTouchingWater(double x, double y, double z) {
        FluidState fluidState = this.mc.world.getFluidState(new Mutable(x, y, z));
        if (fluidState.getFluid() != Fluids.WATER && fluidState.getFluid() != Fluids.FLOWING_WATER) {
            return false;
        } else {
            return y - (double) ((int) y) <= (double) fluidState.getHeight();
        }
    }

    private HitResult getCollision(EnderPearlEntity pearl, Vec3d pos, Vec3d vel) {
        Vec3d vec3d3 = pos;
        Vec3d vec3d4 = pos.add(vel);
        HitResult hitResult = this.mc.world.raycast(new RaycastContext(pos, vec3d4, ShapeType.COLLIDER, FluidHandling.NONE, pearl));
        if (hitResult.getType() != Type.MISS) {
            vec3d3 = hitResult.getPos();
        }

        HitResult hitResult2 = ProjectileUtil.getEntityCollision(this.mc.world, pearl, vec3d3, vec3d4, pearl.getBoundingBox().stretch(pearl.getVelocity()).expand(1.0), entity -> !entity.isSpectator() && entity.isAlive() && entity.canHit());
        if (hitResult2 != null) {
            hitResult = hitResult2;
        }

        return hitResult;
    }

    private class Path {
        private final List<Vector3d> points = new ArrayList();
        private Entity entity;

        public void clear() {
            for (Vector3d point : this.points) {
                PearlPredict.this.vec3s.free(point);
            }

            this.points.clear();
            this.entity = null;
        }

        public void calculate(EnderPearlEntity pearl) {
            Vec3d pos = pearl.getPos();
            double posX = pos.x;
            double posY = pos.y;
            double posZ = pos.z;
            Vec3d vel = pearl.getVelocity();
            double velX = vel.x;
            double velY = vel.y;
            double velZ = vel.z;

            while (true) {
                posX += velX;
                posY += velY;
                posZ += velZ;
                pos = new Vec3d(posX, posY, posZ);
                double drag;
                if (PearlPredict.this.isTouchingWater(posX, posY, posZ)) {
                    drag = 0.8;
                } else {
                    drag = 0.99;
                }

                velX *= drag;
                double var23 = velY * drag;
                velZ *= drag;
                velY = var23 - 0.03;
                vel = new Vec3d(velX, velY, velZ);
                HitResult hitResult = PearlPredict.this.getCollision(pearl, pos, vel);
                if (hitResult.getType() != Type.MISS) {
                    this.processHitResult(hitResult);
                    new BlockPos(new Vec3i((int) pos.x, (int) pos.y, (int) pos.z));
                    break;
                }

                if (pos.y < 0.0) {
                    break;
                }

                int chunkX = (int) (pos.x / 16.0);
                int chunkZ = (int) (pos.z / 16.0);
                if (!PearlPredict.this.mc.world.getChunkManager().isChunkLoaded(chunkX, chunkZ)) {
                    break;
                }

                this.addPoint(new Vector3d(pos.x, pos.y, pos.z));
            }
        }

        private void addPoint(Vector3d vec3) {
            this.points.add(vec3);
        }

        private void processHitResult(HitResult result) {
            if (result.getType() == Type.BLOCK) {
                this.points.add(PearlPredict.this.vec3s.get().set(result.getPos().x, result.getPos().y, result.getPos().z));
            } else if (result.getType() == Type.ENTITY) {
                this.entity = ((EntityHitResult) result).getEntity();
                this.points.add(PearlPredict.this.vec3s.get().set(result.getPos().x, result.getPos().y, result.getPos().z).add(0.0, this.entity.getHeight() / 2.0F, 0.0));
            }
        }

        public void render(Render3DEvent event) {
            Vector3d lastPoint = null;

            for (Vector3d point : this.points) {
                if (lastPoint != null) {
                    event.renderer.line(lastPoint.x, lastPoint.y, lastPoint.z, point.x, point.y, point.z, PearlPredict.this.lineColor.get());
                }

                lastPoint = point;
            }

            if (lastPoint != null) {
                event.renderer.box(lastPoint.x - 0.3, lastPoint.y, lastPoint.z - 0.3, lastPoint.x + 0.3, lastPoint.y + 1.8, lastPoint.z + 0.3, PearlPredict.this.sideColor.get(), PearlPredict.this.lineColor.get(), PearlPredict.this.shapeMode.get(), 0);
            }

            if (this.entity != null) {
                double x = MathHelper.lerp(event.tickDelta, this.entity.lastRenderX, this.entity.getX()) - this.entity.getX();
                double y = MathHelper.lerp(event.tickDelta, this.entity.lastRenderY, this.entity.getY()) - this.entity.getY();
                double z = MathHelper.lerp(event.tickDelta, this.entity.lastRenderZ, this.entity.getZ()) - this.entity.getZ();
                Box box = this.entity.getBoundingBox();
                event.renderer.box(x + box.minX, y + box.minY, z + box.minZ, x + box.maxX, y + box.maxY, z + box.maxZ, PearlPredict.this.sideColor.get(), PearlPredict.this.lineColor.get(), PearlPredict.this.shapeMode.get(), 0);
            }
        }
    }
}
