package venomhack.utils;

import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Iterator;

public class FakePlayerUtils {
    public static void onStatusEffectRemoved(FakePlayerEntity entity, StatusEffectInstance effect) {
        effect.getEffectType().onRemoved(entity, entity.getAttributes(), effect.getAmplifier());
    }

    public static void clearStatusEffects(FakePlayerEntity entity) {
        Iterator<StatusEffectInstance> iterator = entity.getActiveStatusEffects().values().iterator();

        while (iterator.hasNext()) {
            onStatusEffectRemoved(entity, iterator.next());
            iterator.remove();
        }
    }

    public static void addStatusEffect(FakePlayerEntity entity, StatusEffectInstance effect) {
        StatusEffectInstance statusEffectInstance = entity.getActiveStatusEffects().get(effect.getEffectType());
        if (statusEffectInstance == null) {
            entity.getActiveStatusEffects().put(effect.getEffectType(), effect);
            effect.getEffectType().onApplied(entity, entity.getAttributes(), effect.getAmplifier());
        } else if (statusEffectInstance.upgrade(effect)) {
            StatusEffect statusEffect = effect.getEffectType();
            statusEffect.onRemoved(entity, entity.getAttributes(), effect.getAmplifier());
            statusEffect.onApplied(entity, entity.getAttributes(), effect.getAmplifier());
        }
    }

    public static void updatePose(FakePlayerEntity entity) {
        EntityPose entityPose = EntityPose.STANDING;
        if (entity.isFallFlying()) {
            entityPose = EntityPose.FALL_FLYING;
        } else if (entity.isSleeping()) {
            entityPose = EntityPose.SLEEPING;
        } else if (entity.isSwimming()) {
            entityPose = EntityPose.SWIMMING;
        } else if (entity.isUsingRiptide()) {
            entityPose = EntityPose.SPIN_ATTACK;
        } else if (entity.isSneaking() && !entity.getAbilities().flying) {
            entityPose = EntityPose.CROUCHING;
        } else {
            entityPose = EntityPose.STANDING;
        }

        entity.setPose(entityPose);
    }
}
