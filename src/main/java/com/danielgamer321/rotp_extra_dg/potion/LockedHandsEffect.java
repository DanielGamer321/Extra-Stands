package com.danielgamer321.rotp_extra_dg.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;

public class LockedHandsEffect extends LockedPositionEffect {

    public LockedHandsEffect(int liquidColor) {
        super(liquidColor);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeModifierManager modifiers, int amplifier) {
        super.addAttributeModifiers(entity, modifiers, amplifier);
        if (entity instanceof MobEntity && !((MobEntity) entity).isNoAi()) {
            ((MobEntity) entity).setNoAi(true);
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager modifiers, int amplifier) {
        super.removeAttributeModifiers(entity, modifiers, amplifier);
        if (entity instanceof MobEntity && ((MobEntity) entity).isNoAi()) {
            ((MobEntity) entity).setNoAi(false);
        }
    }
}
