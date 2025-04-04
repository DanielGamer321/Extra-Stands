package com.danielgamer321.rotp_extra_dg.init;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.entity.KWBlockEntity;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.KWItemEntity;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.*;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpExtraAddon.MOD_ID);


    public static final RegistryObject<EntityType<KWItemEntity>> KW_ITEM = ENTITIES.register("kw_item",
            () -> EntityType.Builder.<KWItemEntity>of(KWItemEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "kw_item").toString()));



    public static final RegistryObject<EntityType<KWBlockEntity>> KW_BLOCK = ENTITIES.register("kw_block",
            () -> EntityType.Builder.<KWBlockEntity>of(KWBlockEntity::new, EntityClassification.MISC).sized(1.0F, 1.0F).setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(false).fireImmune()
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "kw_block").toString()));



    public static final RegistryObject<EntityType<SFStringEntity>> SF_STRING = ENTITIES.register("sf_string", 
            () -> EntityType.Builder.<SFStringEntity>of(SFStringEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
            .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFUStringEntity>> SFU_STRING = ENTITIES.register("sfu_string",
            () -> EntityType.Builder.<SFUStringEntity>of(SFUStringEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFUStringSweepEntity>> SFU_STRING_SWEEP = ENTITIES.register("sfu_string_sweep",
            () -> EntityType.Builder.<SFUStringSweepEntity>of(SFUStringSweepEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).noSummon()
            .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFUStringWhipEntity>> SFU_STRING_WHIP = ENTITIES.register("sfu_string_whip",
            () -> EntityType.Builder.<SFUStringWhipEntity>of(SFUStringWhipEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).noSummon()
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFExtendedPunchEntity>> SF_EXTENDED_PUNCH = ENTITIES.register("sf_extended_punch",
            () -> EntityType.Builder.<SFExtendedPunchEntity>of(SFExtendedPunchEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFUStringSpikeEntity>> SFU_STRING_SPIKE = ENTITIES.register("sfu_string_spike",
            () -> EntityType.Builder.<SFUStringSpikeEntity>of(SFUStringSpikeEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).noSummon().noSave().setUpdateInterval(20)
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFGrapplingStringEntity>> SF_GRAPPLING_STRING = ENTITIES.register("sf_grappling_string",
            () -> EntityType.Builder.<SFGrapplingStringEntity>of(SFGrapplingStringEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
            .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));

    public static final RegistryObject<EntityType<SFUGrapplingStringEntity>> SFU_GRAPPLING_STRING = ENTITIES.register("sfu_grappling_string",
            () -> EntityType.Builder.<SFUGrapplingStringEntity>of(SFUGrapplingStringEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setUpdateInterval(20)
                    .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));
    
    public static final RegistryObject<EntityType<SFUBarrierEntity>> SFU_BARRIER = ENTITIES.register("sfu_barrier",
            () -> EntityType.Builder.<SFUBarrierEntity>of(SFUBarrierEntity::new, EntityClassification.MISC).sized(0.25F, 0.25F).noSummon().noSave().setShouldReceiveVelocityUpdates(false).setUpdateInterval(Integer.MAX_VALUE)
            .build(new ResourceLocation(RotpExtraAddon.MOD_ID, "sf_strings").toString()));
}
