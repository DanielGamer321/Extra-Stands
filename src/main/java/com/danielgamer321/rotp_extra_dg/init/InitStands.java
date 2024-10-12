package com.danielgamer321.rotp_extra_dg.init;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.entity.stand.stands.*;
import com.danielgamer321.rotp_extra_dg.action.stand.*;
import com.danielgamer321.rotp_extra_dg.power.impl.stand.type.*;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.util.mod.StoryPart;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandEntityAction.Phase;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import static com.github.standobyte.jojo.init.ModEntityTypes.ENTITIES;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpExtraAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpExtraAddon.MOD_ID);

    // ======================================== The Hand ========================================

    public static final RegistryObject<StandEntityAction> THE_HAND_PUNCH = ACTIONS.register("the_hand_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.THE_HAND_PUNCH_LIGHT)));

    public static final RegistryObject<StandEntityAction> THE_HAND_BARRAGE = ACTIONS.register("the_hand_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.THE_HAND_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> THE_HAND_KICK = ACTIONS.register("the_hand_kick",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .resolveLevelToUnlock(1)
                    .punchSound(InitSounds.THE_HAND_KICK_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> THE_HAND_HEAVY_PUNCH = ACTIONS.register("the_hand_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.THE_HAND_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(THE_HAND_KICK)
                    .shiftVariationOf(THE_HAND_PUNCH).shiftVariationOf(THE_HAND_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> THE_HAND_ERASE = ACTIONS.register("the_hand_erase",
            () -> new TheHandErase(new TheHandErase.Builder().holdToFire(20, false).standUserWalkSpeed(1.0F).standPerformDuration(1)
                    .resolveLevelToUnlock(2)
                    .standPose(TheHandErase.ERASE_POSE)
                    .punchSound(() -> null).swingSound(InitSounds.THE_HAND_ERASE)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityMeleeBarrage> THE_HAND_ERASURE_BARRAGE = ACTIONS.register("the_hand_erasure_barrage",
            () -> new TheHandErasureBarrage(new TheHandErasureBarrage.Builder().staminaCostTick(20F).cooldown(170)
                    .resolveLevelToUnlock(4)
                    .autoSummonStand()
                    .standPose(TheHandErasureBarrage.ERASURE_BARRAGE_POSE)
                    .barrageSwingSound(InitSounds.THE_HAND_ERASURE_BARRAGE).barrageHitSound(null)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> THE_HAND_BLOCK = ACTIONS.register("the_hand_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<StandEntityAction> THE_HAND_ERASE_ITEM = ACTIONS.register("the_hand_erase_item",
            () -> new TheHandEraseItem(new TheHandEraseItem.Builder().holdType().staminaCostTick(1F)
                    .resolveLevelToUnlock(3)
                    .standOffsetFromUser(0.667, 0.2, 0).standPose(TheHandEraseItem.ERASE_ITEM_POSE)
                    .partsRequired(StandPart.ARMS)
                    .standSound(InitSounds.THE_HAND_ERASURE_BARRAGE)));


    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<TheHandEntity>> STAND_THE_HAND =
            new EntityStandRegistryObject<>("the_hand",
                    STANDS,
                    () -> new EntityStandType.Builder<>()
                            .color(0xEDEEF0)
                            .storyPartName(StoryPart.DIAMOND_IS_UNBREAKABLE.getName())
                            .leftClickHotbar(
                                    THE_HAND_PUNCH.get(),
                                    THE_HAND_BARRAGE.get(),
                                    THE_HAND_ERASE.get(),
                                    THE_HAND_ERASURE_BARRAGE.get()
                            )
                            .rightClickHotbar(
                                    THE_HAND_BLOCK.get(),
                                    THE_HAND_ERASE_ITEM.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(12.0)
                                    .speed(11.0, 12.0)
                                    .range(2.0, 4.0)
                                    .durability(10.0)
                                    .precision(8.0, 10.0)
                                    .randomWeight(1)
                            )
                            .addSummonShout(InitSounds.OKUYASU_THE_HAND)
                            .addOst(InitSounds.THE_HAND_OST)
                            .build(),

                    ENTITIES,
                    () -> new StandEntityType<TheHandEntity>(TheHandEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.THE_HAND_SUMMON)
                            .unsummonSound(InitSounds.THE_HAND_UNSUMMON))
                    .withDefaultStandAttributes();



    // ======================================== Kraft Work ========================================

    public static final RegistryObject<StandEntityAction> KRAFT_WORK_PUNCH = ACTIONS.register("kraft_work_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.KRAFT_WORK_PUNCH_LIGHT)));

    public static final RegistryObject<StandEntityAction> KRAFT_WORK_BARRAGE = ACTIONS.register("kraft_work_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.KRAFT_WORK_BARRAGE)));

    public static final RegistryObject<StandEntityHeavyAttack> KRAFT_WORK_COMBO_PUNCH = ACTIONS.register("kraft_work_combo_punch",
            () -> new KraftWorkComboPunch(new StandEntityHeavyAttack.Builder()
                    .resolveLevelToUnlock(1)
                    .punchSound(InitSounds.KRAFT_WORK_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityActionModifier> KRAFT_WORK_LOCK_ARMOR = ACTIONS.register("kraft_work_lock_armor",
            () -> new KraftWorkLockArmor(new StandAction.Builder()));

    public static final RegistryObject<KraftWorkHeavyPunch> KRAFT_WORK_HEAVY_PUNCH = ACTIONS.register("kraft_work_heavy_punch",
            () -> new KraftWorkHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.KRAFT_WORK_PUNCH_HEAVY)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(KRAFT_WORK_COMBO_PUNCH)
                    .shiftVariationOf(KRAFT_WORK_PUNCH).shiftVariationOf(KRAFT_WORK_BARRAGE)));

    public static final RegistryObject<StandAction> KRAFT_WORK_PlACE_PROJECTILE = ACTIONS.register("kraft_work_place_projectile",
            () -> new KraftWorkPlaceProjectile(new StandAction.Builder().staminaCost(10F)
                    .resolveLevelToUnlock(2)));

    public static final RegistryObject<StandEntityAction> KRAFT_WORK_BLOCK = ACTIONS.register("kraft_work_block",
            () -> new KraftWorkBlock(new StandEntityBlock.Builder()));

    public static final RegistryObject<StandAction> KRAFT_WORK_LOCK_YOURSELF = ACTIONS.register("kraft_work_lock_yourself",
            () -> new KraftWorkLockYourself(new StandAction.Builder()
                    .resolveLevelToUnlock(1)));

    public static final RegistryObject<StandAction> KRAFT_WORK_LOCK_TARGET = ACTIONS.register("kraft_work_lock_target",
            () -> new KraftWorkLockTarget(new StandAction.Builder().staminaCost(10).holdToFire(10, false)
                    .resolveLevelToUnlock(1)));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_TARGET = ACTIONS.register("kraft_work_release_target",
            () -> new KraftWorkReleaseTarget(new StandAction.Builder().staminaCost(1)
                    .resolveLevelToUnlock(1)
                    .ignoresPerformerStun()
                    .shiftVariationOf(KRAFT_WORK_LOCK_TARGET)));

    public static final RegistryObject<StandEntityAction> KRAFT_WORK_ENERGY_ACCUMULATION = ACTIONS.register("kraft_work_energy_accumulation",
            () -> new KraftWorkEnergyAccumulation(new StandEntityAction.Builder().staminaCostTick(1F).standUserWalkSpeed(0.0F)
                    .resolveLevelToUnlock(2)
                    .standOffsetFront().standPose(KraftWorkEnergyAccumulation.GIVE_ENERGY_POSE)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_PROJECTILE = ACTIONS.register("kraft_work_release_projectile",
            () -> new KraftWorkReleaseProjectile(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_ALL_PROJECTILES = ACTIONS.register("kraft_work_release_all_projectiles",
            () -> new KraftWorkReleaseAllProjectiles(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_PROJECTILES_R = ACTIONS.register("kraft_work_release_projectiles_r",
            () -> new KraftWorkReleaseProjectilesR(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_PROJECTILES_NR = ACTIONS.register("kraft_work_release_projectiles_nr",
            () -> new KraftWorkReleaseProjectilesNR(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_ENDER_PEARL = ACTIONS.register("kraft_work_release_ender_pearl",
            () -> new KraftWorkReleaseEnderPearl(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_RELEASE_BENEFICIAL = ACTIONS.register("kraft_work_release_beneficial",
            () -> new KraftWorkReleaseBeneficial(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()));

    public static final RegistryObject<StandAction> KRAFT_WORK_ADVANCED_RELEASE = ACTIONS.register("kraft_work_advanced_release",
            () -> new KraftWorkAdvancedRelease(new StandAction.Builder()
                    .resolveLevelToUnlock(2)
                    .ignoresPerformerStun()
                    .shiftVariationOf(KRAFT_WORK_RELEASE_PROJECTILE)));

    public static final RegistryObject<StandAction> KRAFT_WORK_BI_STATUS = ACTIONS.register("kraft_work_bi_status",
            () -> new KraftWorkBlockingItemsStatus(new StandAction.Builder()
                    .resolveLevelToUnlock(3).isTrained()
                    .ignoresPerformerStun()));


    public static final EntityStandRegistryObject<KraftWorkStandType<StandStats>, StandEntityType<KraftWorkEntity>> STAND_KRAFT_WORK =
            new EntityStandRegistryObject<>("kraft_work",
                    STANDS,
                    () -> new KraftWorkStandType.Builder<>()
                            .color(0xA4C686)
                            .storyPartName(StoryPart.GOLDEN_WIND.getName())
                            .leftClickHotbar(
                                    KRAFT_WORK_PUNCH.get(),
                                    KRAFT_WORK_BARRAGE.get(),
                                    KRAFT_WORK_PlACE_PROJECTILE.get()
                            )
                            .rightClickHotbar(
                                    KRAFT_WORK_BLOCK.get(),
                                    KRAFT_WORK_LOCK_YOURSELF.get(),
                                    KRAFT_WORK_LOCK_TARGET.get(),
                                    KRAFT_WORK_ENERGY_ACCUMULATION.get(),
                                    KRAFT_WORK_RELEASE_PROJECTILE.get(),
                                    KRAFT_WORK_BI_STATUS.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(14.0)
                                    .speed(14.0, 16.0)
                                    .range(2.0, 2.0)
                                    .durability(10.0)
                                    .precision(2.0, 4.5)
                                    .randomWeight(2)
                            )
                            .addSummonShout(InitSounds.SALE_KRAFT_WORK)
                            .addOst(InitSounds.KRAFT_WORK_OST)
                            .build(),

                    ENTITIES,
                    () -> new StandEntityType<KraftWorkEntity>(KraftWorkEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.KRAFT_WORK_SUMMON)
                            .unsummonSound(InitSounds.KRAFT_WORK_UNSUMMON))
                    .withDefaultStandAttributes();



    // ======================================== Stone Free ========================================

    public static final RegistryObject<StandEntityLightAttack> STONE_FREE_PUNCH = ACTIONS.register("stone_free_punch",
            () -> new StoneFreePunch(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.STONE_FREE_PUNCH_LIGHT)
                    .standSound(Phase.WINDUP, InitSounds.STONE_FREE_ORA)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_STRING_ATTACK = ACTIONS.register("stone_free_user_string_attack",
            () -> new StoneFreeUserStringAttack(new StandAction.Builder().staminaCost(20)
                    .needsFreeMainHand().swingHand()));

    public static final RegistryObject<StandEntityAction> STONE_FREE_BARRAGE = ACTIONS.register("stone_free_barrage",
            () -> new StoneFreeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.STONE_FREE_BARRAGE)
                    .standSound(InitSounds.STONE_FREE_ORA_ORA_ORA)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_STRING_SWEEP = ACTIONS.register("stone_free_user_string_sweep",
            () -> new StoneFreeUserStringSweep(new StandAction.Builder().staminaCost(35)
                    .needsFreeMainHand().swingHand()));

    public static final RegistryObject<StandEntityHeavyAttack> STONE_FREE_STRING_PUNCH = ACTIONS.register("stone_free_string_punch",
            () -> new StoneFreeStringPunch(new StandEntityHeavyAttack.Builder()
                    .resolveLevelToUnlock(1)
                    .punchSound(InitSounds.STONE_FREE_PUNCH_HEAVY)
                    .standSound(Phase.WINDUP, InitSounds.STONE_FREE_ORA_LONG)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityActionModifier> STONE_FREE_STRING_CAPTURE = ACTIONS.register("stone_free_string_capture",
            () -> new StoneFreeStringCapture(new StandEntityActionModifier.Builder().staminaCost(75).cooldown(24, 100, 0.5F)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> STONE_FREE_HEAVY_PUNCH = ACTIONS.register("stone_free_heavy_punch",
            () -> new StoneFreeHeavyPunch(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.STONE_FREE_PUNCH_HEAVY)
                    .standOffsetFromUser(-0.75, -0.75).standSound(Phase.WINDUP, InitSounds.STONE_FREE_ORA_LONG)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(STONE_FREE_STRING_PUNCH)
                    .shiftVariationOf(STONE_FREE_PUNCH).shiftVariationOf(STONE_FREE_BARRAGE)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_STRING_WHIP = ACTIONS.register("stone_free_user_string_whip",
            () -> new StoneFreeUserStringWhip(new StandAction.Builder().staminaCost(40).cooldown(10, 20)
                    .needsFreeMainHand().swingHand()
                    .shiftVariationOf(STONE_FREE_USER_STRING_ATTACK).shiftVariationOf(STONE_FREE_USER_STRING_SWEEP)));

    public static final RegistryObject<StandEntityAction> STONE_FREE_STRING_BIND = ACTIONS.register("stone_free_attack_binding",
            () -> new StoneFreeStringBind(new StandEntityAction.Builder().staminaCost(35).standPerformDuration(24).cooldown(24, 100, 0.5F)
                    .standSound(InitSounds.STONE_FREE_STRING)
                    .resolveLevelToUnlock(1)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_STRING_BIND = ACTIONS.register("stone_free_user_attack_binding",
            () -> new StoneFreeUserStringBind(new StandAction.Builder().staminaCost(35).cooldown(24, 100, 0.5F)
                    .needsFreeMainHand().swingHand()));

    public static final RegistryObject<StandEntityAction> STONE_FREE_EXTENDED_PUNCH = ACTIONS.register("stone_free_extended_punch",
            () -> new StoneFreeExtendedPunch(new StandEntityAction.Builder().staminaCost(375).standPerformDuration(10).cooldown(10, 100)
                    .resolveLevelToUnlock(3)
                    .standOffsetFront().standPose(StandPose.RANGED_ATTACK).standSound(InitSounds.STONE_FREE_STRING)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_STRING_PICK = ACTIONS.register("stone_free_user_string_pick",
            () -> new StoneFreeUserStringPick(new StandAction.Builder().holdToFire(5, false).staminaCost(35).cooldown(16, 100)
                    .needsFreeMainHand().swingHand()));

    public static final RegistryObject<StandEntityAction> STONE_FREE_BLOCK = ACTIONS.register("stone_free_block",
            () -> new StandEntityBlock());

    public static final RegistryObject<StandAction> STONE_FREE_USER_GRAPPLE = ACTIONS.register("stone_free_user_grapple",
            () -> new StoneFreeUserGrapple(new StandAction.Builder().staminaCostTick(20)
                    .needsFreeMainHand().swingHand()
                    .resolveLevelToUnlock(2)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_GRAPPLE_ENTITY = ACTIONS.register("stone_free_user_grapple_entity",
            () -> new StoneFreeUserGrapple(new StandAction.Builder().staminaCostTick(20)
                    .needsFreeMainHand().swingHand()
                    .shiftVariationOf(STONE_FREE_USER_GRAPPLE)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_RECOVER_STRING = ACTIONS.register("stone_free_user_recover_string",
            () -> new StoneFreeUserRecoverString(new StandAction.Builder().holdType()));

    public static final RegistryObject<StandEntityAction> STONE_FREE_GRAPPLE_ENTITY = ACTIONS.register("stone_free_grapple_entity",
            () -> new StoneFreeGrapple(new StandEntityAction.Builder().staminaCostTick(1).holdType().standUserWalkSpeed(1.0F)
                    .standPose(StandPose.RANGED_ATTACK).standOffsetFromUser(-0.5, 0.25)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_CLOSE_WOUNDS = ACTIONS.register("stone_free_user_close_wounds",
            () -> new StoneFreeUserCloseWounds(new StandAction.Builder().staminaCost(20).holdToFire(10, false)
                    .resolveLevelToUnlock(2)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_BARRIER = ACTIONS.register("stone_free_user_barrier",
            () -> new StoneFreeUserBarrier(new StandAction.Builder()
                    .needsFreeMainHand().swingHand()
                    .resolveLevelToUnlock(3)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_REMOVE_BARRIER = ACTIONS.register("stone_free_user_remove_barrier",
            () -> new StoneFreeUserRemoveBarrier(new StandAction.Builder().holdType()
                    .resolveLevelToUnlock(3)
                    .shiftVariationOf(STONE_FREE_USER_BARRIER)));

    public static final RegistryObject<StandAction> STONE_FREE_USER_MOBIUS_STRIP = ACTIONS.register("stone_free_user_mobius_strip",
            () -> new StoneFreeUserMobiusStrip(new StandAction.Builder()
                    .noResolveUnlock()));


    public static final EntityStandRegistryObject<StoneFreeStandType<StandStats>, StandEntityType<StoneFreeEntity>> STAND_STONE_FREE =
            new EntityStandRegistryObject<>("stone_free",
                    STANDS,
                    () -> new StoneFreeStandType.Builder<>()
                            .color(0x80DEF7)
                            .storyPartName(StoryPart.STONE_OCEAN.getName())
                            .leftClickHotbar(
                                    STONE_FREE_PUNCH.get(),
                                    STONE_FREE_BARRAGE.get(),
                                    STONE_FREE_STRING_BIND.get(),
                                    STONE_FREE_EXTENDED_PUNCH.get()
                            )
                            .rightClickHotbar(
                                    STONE_FREE_BLOCK.get(),
                                    STONE_FREE_USER_GRAPPLE.get(),
                                    STONE_FREE_USER_CLOSE_WOUNDS.get(),
                                    STONE_FREE_USER_BARRIER.get(),
                                    STONE_FREE_USER_MOBIUS_STRIP.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(14.0, 15.0)
                                    .speed(11.0, 12.0)
                                    .range(2.0, 10.0)
                                    .durability(14.0, 16.0)
                                    .precision(10.0)
                                    .randomWeight(2)
                            )
                            .addSummonShout(InitSounds.JOLYNE_STONE_FREE)
                            .addOst(InitSounds.STONE_FREE_OST)
                            .build(),

                    ENTITIES,
                    () -> new StandEntityType<StoneFreeEntity>(StoneFreeEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.STONE_FREE_SUMMON)
                            .unsummonSound(InitSounds.STONE_FREE_UNSUMMON))
                    .withDefaultStandAttributes();
}
