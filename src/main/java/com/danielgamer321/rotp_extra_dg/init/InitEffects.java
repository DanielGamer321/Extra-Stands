package com.danielgamer321.rotp_extra_dg.init;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.potion.*;
import com.github.standobyte.jojo.potion.ImmobilizeEffect;
import com.github.standobyte.jojo.potion.StatusEffect;
import com.github.standobyte.jojo.potion.StunEffect;
import com.google.common.collect.ImmutableSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

@EventBusSubscriber(modid = RotpExtraAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class InitEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, RotpExtraAddon.MOD_ID);

    public static final RegistryObject<Effect> ERASED = EFFECTS.register("erased",
            () -> new StatusEffect(EffectType.HARMFUL, 0xCAF6F4));

    public static final RegistryObject<StunEffect> SURPRISE = EFFECTS.register("surprise",
            () -> new SurpriseEffect(0xDD0F0F).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_POSITION = EFFECTS.register("locked_position",
            () -> new LockedPositionEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_MAIN_HAND = EFFECTS.register("locked_main_hand",
            () -> new LockedHandsEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_OFF_HAND = EFFECTS.register("locked_off_hand",
            () -> new LockedHandsEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_HELMET = EFFECTS.register("locked_helmet",
            () -> new LockedPositionEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_CHESTPLATE = EFFECTS.register("locked_chestplate",
            () -> new LockedPositionEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> LOCKED_LEGGINGS = EFFECTS.register("locked_leggings",
            () -> new LockedPositionEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> TRANSPORT_LOCKED = EFFECTS.register("transportation_locked",
            () -> new LockedPositionEffect(0xDB4736).setUncurable());

    public static final RegistryObject<ImmobilizeEffect> FULL_TRANSPORT_LOCKED = EFFECTS.register("full_transportation_locked",
            () -> new TransportLockedEffect(0xDB4736).setUncurable());
    
    public static final RegistryObject<StringDecompositionEffect> STRING_DECOMPOSITION = EFFECTS.register("string_decomposition", 
            () -> new StringDecompositionEffect(EffectType.NEUTRAL, 0x80DEF7));

    public static final RegistryObject<MobiusStripEffect> MOBIUS_STRIP = EFFECTS.register("mobius_strip",
            () -> new MobiusStripEffect(EffectType.BENEFICIAL, 0x80DEF7));

    private static Set<Effect> TRACKED_EFFECTS;
    @SubscribeEvent(priority = EventPriority.LOW)
    public static final void afterEffectsRegister(RegistryEvent.Register<Effect> event) {
        TRACKED_EFFECTS = ImmutableSet.of(
                ERASED.get(),
                SURPRISE.get(),
                LOCKED_POSITION.get(),
                LOCKED_MAIN_HAND.get(),
                LOCKED_OFF_HAND.get(),
                LOCKED_HELMET.get(),
                LOCKED_CHESTPLATE.get(),
                LOCKED_LEGGINGS.get(),
                TRANSPORT_LOCKED.get(),
                FULL_TRANSPORT_LOCKED.get(),
                STRING_DECOMPOSITION.get(),
                MOBIUS_STRIP.get()
        );
    }

    public static boolean isEffectTracked(Effect effect) {
        return TRACKED_EFFECTS.contains(effect);
    }

    public static boolean isLocked(LivingEntity entity) {
        return entity.hasEffect(LOCKED_POSITION.get()) || lockedArms(entity) ||
                shiftLocked(entity);
    }

    public static boolean IADisabled(LivingEntity entity) {
        return entity.hasEffect(SURPRISE.get()) || lockedArms(entity) || entity.hasEffect(TRANSPORT_LOCKED.get()) ||
                entity.hasEffect(FULL_TRANSPORT_LOCKED.get());
    }

    public static boolean lockedArms(LivingEntity entity) {
        return entity.hasEffect(LOCKED_MAIN_HAND.get()) || entity.hasEffect(LOCKED_OFF_HAND.get());
    }

    public static boolean shiftLocked(LivingEntity entity) {
        return entity.hasEffect(LOCKED_HELMET.get()) || entity.hasEffect(LOCKED_CHESTPLATE.get()) ||
                entity.hasEffect(LOCKED_LEGGINGS.get()) || entity.hasEffect(TRANSPORT_LOCKED.get()) ||
                entity.hasEffect(FULL_TRANSPORT_LOCKED.get());
    }

    public static boolean lockedInventory(LivingEntity entity) {
        return lockedArms(entity) || entity.hasEffect(LOCKED_CHESTPLATE.get()) ||
                entity.hasEffect(LOCKED_LEGGINGS.get()) || entity.hasEffect(TRANSPORT_LOCKED.get()) ||
                entity.hasEffect(FULL_TRANSPORT_LOCKED.get());
    }
}
