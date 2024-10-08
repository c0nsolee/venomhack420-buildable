package venomhack.utils.customObjects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PlacementInfo {
    private BlockPos blockPos;
    private Vec3d pos;
    private Direction direction;
    private LivingEntity target;
    private float damage = 0.0F;
    private float selfDamage = 0.0F;
    private float friendDamage = 0.0F;
    private byte pops = 0;
    private byte friendPops = 0;
    private byte surroundBreak = 0;
    private boolean faceplace = false;
    private boolean support = false;
    private boolean hasToBreak = false;
    private boolean selfPop = false;

    public PlacementInfo() {
    }

    public PlacementInfo(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setSelfPop() {
        this.selfPop = true;
    }

    public void setVec3d(Vec3d vec3d) {
        this.pos = vec3d;
    }

    public void addFriendDamage(float friendDamage) {
        this.friendDamage += friendDamage;
    }

    public void incrementFriendPops() {
        ++this.friendPops;
    }

    public void incrementPops() {
        ++this.pops;
    }

    public void setShouldBreak(boolean shouldBreak) {
        this.hasToBreak = shouldBreak;
    }

    public void reset(LivingEntity target) {
        this.faceplace = false;
        this.surroundBreak = 0;
        this.target = target;
    }

    public void reset(LivingEntity target, float damage) {
        this.faceplace = false;
        this.surroundBreak = 0;
        this.damage = damage;
        this.target = target;
    }

    public void copy(PlacementInfo oldPlace) {
        this.blockPos = new BlockPos(oldPlace.blockPos);
        this.pos = oldPlace.getPos();
        this.direction = oldPlace.direction;
        this.target = oldPlace.target;
        this.damage = oldPlace.damage;
        this.selfDamage = oldPlace.selfDamage;
        this.friendDamage = oldPlace.friendDamage;
        this.selfPop = oldPlace.selfPop;
        this.pops = oldPlace.pops;
        this.friendPops = oldPlace.friendPops;
        this.surroundBreak = oldPlace.surroundBreak;
        this.faceplace = oldPlace.faceplace;
        this.support = oldPlace.support;
        this.hasToBreak = oldPlace.hasToBreak;
    }

    public LivingEntity getTarget() {
        return this.target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getSelfDamage() {
        return this.selfDamage;
    }

    public void setSelfDamage(float selfDamage) {
        this.selfDamage = selfDamage;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public Vec3d getPos() {
        return this.pos;
    }

    public byte getPops() {
        return this.pops;
    }

    public byte getSurroundBreak() {
        return this.surroundBreak;
    }

    public void setSurroundBreak(byte surroundBreak) {
        this.surroundBreak = surroundBreak;
    }

    public boolean isFaceplace() {
        return this.faceplace;
    }

    public void setFaceplace(boolean faceplace) {
        this.faceplace = faceplace;
    }

    public boolean isSupport() {
        return this.support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public boolean shouldBreak() {
        return this.hasToBreak;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float value(LivingEntity lastPlacetarget) {
        return this.damage * (float) (this.target != null && this.target.equals(lastPlacetarget) ? 2 : 1) - this.selfDamage + (float) (this.pops * 1000) - (float) (this.selfPop ? 10000 : 0) - this.friendDamage - (float) (this.friendPops * 100);
    }
}
