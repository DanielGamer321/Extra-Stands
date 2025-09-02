package com.danielgamer321.rotp_extra_dg.init;

import java.util.function.Supplier;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpExtraAddon.MOD_ID);

    static final OstSoundList AQUA_NECKLACE_OST = new OstSoundList(new ResourceLocation(RotpExtraAddon.MOD_ID, "aqua_necklace_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> OKUYASU_THE_HAND = SOUNDS.register("okuyasu_the_hand",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "okuyasu_the_hand")));

    public static final RegistryObject<SoundEvent> THE_HAND_SUMMON = SOUNDS.register("the_hand_summon",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_summon")));

    public static final Supplier<SoundEvent> THE_HAND_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;

    public static final RegistryObject<SoundEvent> THE_HAND_PUNCH_LIGHT = SOUNDS.register("the_hand_punch_light",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_punch_light")));

    public static final RegistryObject<SoundEvent> THE_HAND_PUNCH_HEAVY = SOUNDS.register("the_hand_punch_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_punch_heavy")));

    public static final RegistryObject<SoundEvent> THE_HAND_KICK_HEAVY = SOUNDS.register("the_hand_kick_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_kick_heavy")));

    public static final Supplier<SoundEvent> THE_HAND_BARRAGE = THE_HAND_PUNCH_LIGHT;

    public static final RegistryObject<SoundEvent> THE_HAND_ERASE = SOUNDS.register("the_hand_erase",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_erase")));

    public static final RegistryObject<SoundEvent> THE_HAND_ERASURE_BARRAGE = SOUNDS.register("the_hand_erasure_barrage",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_erasure_barrage")));

    static final OstSoundList THE_HAND_OST = new OstSoundList(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> SALE_KRAFT_WORK = SOUNDS.register("sale_kraft_work",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "sale_kraft_work")));

    public static final Supplier<SoundEvent> KRAFT_WORK_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;

    public static final Supplier<SoundEvent> KRAFT_WORK_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;

    public static final Supplier<SoundEvent> KRAFT_WORK_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;

    public static final Supplier<SoundEvent> KRAFT_WORK_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;

    public static final Supplier<SoundEvent> KRAFT_WORK_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;

    public static final RegistryObject<SoundEvent> KRAFT_WORK_BLOCKS_A_PROJECTILE = SOUNDS.register("kraft_work_blocks_a_projectile",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "kraft_work_blocks_a_projectile")));

    public static final RegistryObject<SoundEvent> KRAFT_WORK_GIVING_KINETIC_ENERGY = SOUNDS.register("kraft_work_giving_kinetic_energy",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "kraft_work_giving_kinetic_energy")));

    public static final RegistryObject<SoundEvent> KRAFT_WORK_RELEASED_PROJECTILE = SOUNDS.register("kraft_work_released_projectile",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "kraft_work_released_projectile")));

    static final OstSoundList KRAFT_WORK_OST = new OstSoundList(new ResourceLocation(RotpExtraAddon.MOD_ID, "kraft_work_ost"), SOUNDS);

    public static final RegistryObject<SoundEvent> JOLYNE_STONE_FREE = SOUNDS.register("jolyne_stone_free",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "jolyne_stone_free")));

    public static final RegistryObject<SoundEvent> STONE_FREE_SUMMON = SOUNDS.register("stone_free_summon",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_summon")));

    public static final Supplier<SoundEvent> STONE_FREE_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;

    public static final RegistryObject<SoundEvent> STONE_FREE_PUNCH_LIGHT = SOUNDS.register("stone_free_punch_light",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_punch_light")));

    public static final RegistryObject<SoundEvent> STONE_FREE_PUNCH_HEAVY = SOUNDS.register("stone_free_punch_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_punch_heavy")));

    public static final Supplier<SoundEvent> STONE_FREE_BARRAGE = InitSounds.STONE_FREE_PUNCH_LIGHT;

    public static final RegistryObject<SoundEvent> STONE_FREE_ORA = SOUNDS.register("stone_free_ora",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_ora")));

    public static final RegistryObject<SoundEvent> STONE_FREE_ORA_LONG = SOUNDS.register("stone_free_ora_long",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_ora_long")));

    public static final RegistryObject<SoundEvent> STONE_FREE_ORA_ORA_ORA = SOUNDS.register("stone_free_ora_ora_ora",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_ora_ora_ora")));

    public static final RegistryObject<SoundEvent> STONE_FREE_STRING = SOUNDS.register("stone_free_string",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_string")));

    public static final RegistryObject<SoundEvent> STONE_FREE_GRAPPLE_CATCH = SOUNDS.register("stone_free_grapple_catch",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_grapple_catch")));

    public static final RegistryObject<SoundEvent> STONE_FREE_CLOSING_WOUND = SOUNDS.register("stone_free_closing_wound",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_closing_wound")));

    public static final RegistryObject<SoundEvent> STONE_FREE_CLOSED_WOUND = SOUNDS.register("stone_free_closed_wound",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_closed_wound")));

    public static final RegistryObject<SoundEvent> STONE_FREE_BARRIER_PLACED = SOUNDS.register("stone_free_barrier_placed",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_barrier_placed")));

    public static final RegistryObject<SoundEvent> STONE_FREE_BARRIER_RIPPED = SOUNDS.register("stone_free_barrier_ripped",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_barrier_ripped")));

    public static final RegistryObject<SoundEvent> STONE_FREE_MOBIUS_STRIP = SOUNDS.register("stone_free_mobius_strip",
            () -> new SoundEvent(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_mobius_strip")));

    static final OstSoundList STONE_FREE_OST = new OstSoundList(new ResourceLocation(RotpExtraAddon.MOD_ID, "stone_free_ost"), SOUNDS);

}
