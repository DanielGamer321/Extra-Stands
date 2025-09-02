package com.danielgamer321.rotp_extra_dg.entity.stand.stands;

import com.danielgamer321.rotp_extra_dg.RotpExtraConfig;
import com.danielgamer321.rotp_extra_dg.action.stand.AquaNecklaceHeavyPunch;
import com.danielgamer321.rotp_extra_dg.init.InitStands;
import com.danielgamer321.rotp_extra_dg.util.AddonInteractionUtil;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCap;
import com.github.standobyte.jojo.capability.entity.PlayerUtilCapProvider;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.entity.MRDetectorEntity;
import com.github.standobyte.jojo.entity.damaging.DamagingEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.MRCrossfireHurricaneEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.MRFireballEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.MRFlameEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.SCRapierEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.MRRedBindEntity;
import com.github.standobyte.jojo.entity.stand.*;
import com.github.standobyte.jojo.entity.stand.stands.SilverChariotEntity;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

import java.util.function.Supplier;

import static com.danielgamer321.rotp_extra_dg.action.stand.TheHandErasureBarrage.getEraseDamage;

public class AquaNecklaceEntity extends StandEntity {

    private StandRelativeOffset offsetDefault = StandRelativeOffset.withYOffset(-0.75, 0.8, -0.75);
    private StandRelativeOffset offsetDefaultArmsOnly = StandRelativeOffset.withYOffset(0, 0.8, 0.15);

    private static final DataParameter<Integer> STATE = EntityDataManager.defineId(AquaNecklaceEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> TARGET_INSIDE_ID = EntityDataManager.defineId(AquaNecklaceEntity.class, DataSerializers.INT);
    private LivingEntity targetInside;

    public AquaNecklaceEntity(StandEntityType<AquaNecklaceEntity> type, World world) {
        super(type, world);
        refreshDimensions();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STATE, 2);
        entityData.define(TARGET_INSIDE_ID, -1);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> dataParameter) {
        super.onSyncedDataUpdated(dataParameter);
        if (dataParameter == TARGET_INSIDE_ID) {
            int entityId = entityData.get(TARGET_INSIDE_ID);
            Entity entity = level.getEntity(entityId);
            if (entity instanceof LivingEntity && this.getUser() != null && this.getUser().isAlive()) {
                targetInside = (LivingEntity) entity;
                entityData.set(TARGET_INSIDE_ID, targetInside.getId());
            }
            else {
                targetInside = null;
                entityData.set(TARGET_INSIDE_ID, -1);
            }
        }
    }

    @Override
    public boolean attackEntity(Supplier<Boolean> doAttack, StandEntityPunch punch, StandEntityTask task) {
        if (isInside()) punch.dmgSource.bypassArmor();
        if (punch.target instanceof LivingEntity && ((LivingEntity)punch.target).isSensitiveToWater()) {
            punch.damage(punch.getDamage() * 1.5F);
        }
        return super.attackEntity(doAttack, punch, task);
    }

    public int getState() {
        return entityData.get(STATE);
    }

    public void setState(int value) {
        entityData.set(STATE, value);
    }

    private boolean crossingBlocks(boolean variant) {
        BlockPos pos = this.blockPosition();
        Block block = level.getBlockState(pos).getBlock();
        BlockPos pos2 = new BlockPos(pos.getX(), this.position().y + 0.76, pos.getZ());
        Block block2 = level.getBlockState(pos2).getBlock();
        boolean defaultReturn = listOfTraversableBlocks(block) && listOfTraversableBlocks(block2);
        return variant ? this.horizontalCollision && defaultReturn : defaultReturn;
    }

    private boolean canPassThroughBlocks(Vector3d vec) {
        double x = 0.0D + vec.x;
        double y = 0.0D + vec.y;
        double z = 0.0D + vec.z;
        boolean fCX = ((x > 0.0D && falseCollision(1, false, true)) || (x < 0.0D && falseCollision(1, true, false))) &&
                !((x < 0.0D && falseCollision(1, false, true)) || (x > 0.0D && falseCollision(1, true, false)));
        boolean fCY = (y > 0.0D && falseCollision(2, false, true)) || (y < 0.0D && falseCollision(2, true, false));
        boolean fCZ = ((z > 0.0D && falseCollision(3, false, true)) || (z < 0.0D && falseCollision(3, true, false))) &&
                !((z < 0.0D && falseCollision(3, false, true)) || (z > 0.0D && falseCollision(3, true, false)));
        return (this.horizontalCollision && (fCX || fCZ)) || (this.verticalCollision && fCY);
    }

    private boolean listOfTraversableBlocks(Block block) {
        return block instanceof DoorBlock || block instanceof TrapDoorBlock || block instanceof FenceBlock || block instanceof FenceGateBlock ||
                block == Blocks.IRON_BARS || block instanceof EndRodBlock || block instanceof ChainBlock;
    }

    public void setTargetInside(@Nullable Entity entity) {
        int entityId;
        if (entity != null) {
            entityId = entity.getId();
        }
        else {
            entityId = -1;
        }

        entityData.set(TARGET_INSIDE_ID, entityId);
    }

    @Nullable
    public LivingEntity getTargetInside() {
        int entityId = entityData.get(TARGET_INSIDE_ID);
        return entityId != -1 ? (LivingEntity) level.getEntity(entityId) : null;
    }

    public boolean isInside() {
        return getTargetInside() != null;
    }

    @Override
    public boolean standHasNoGravity() {
//        if (this.getState() == 1 && !canPassThroughBlocks(this.getDeltaMovement()) && !crossingBlocks() && !isInWater()) {
        if (this.getState() == 1 && !crossingBlocks(false) && !isInWater()) {
            return false;
        }
        return super.standHasNoGravity();
    }

    @Override
    protected double leapBaseStrength() {
        return 6;
    }

    @Override
    public boolean hurt(DamageSource dmgSource, float dmgAmount) {
        if (getState() == 1 && !isInside()) {
            this.level.getEntitiesOfClass(PlayerEntity.class, this.getBoundingBox().inflate(32)).forEach(entity -> {
                if (entity.level.isClientSide() && ClientUtil.canSeeStands()) {
                    this.doWaterSplashEffect();
                }
            });
        }
        return super.hurt(dmgSource, dmgAmount);
    }

    @Override
    protected void actuallyHurt(DamageSource dmgSource, float damageAmount) {
        if (isInside() && getTargetInside() != null) {
            Entity entity = dmgSource.getDirectEntity();
            LivingEntity targetInside = getTargetInside();
            if (AttackedByHisStand(entity, targetInside) && targetInside.isAlive()) {
                if (entity instanceof TheHandEntity && dmgSource.isBypassArmor() && dmgSource.isBypassMagic()) {
                    DamageUtil.hurtThroughInvulTicks(targetInside, getDamageSource((StandEntityDamageSource) dmgSource), damageAmount == getEraseDamage(this, (StandEntity) entity) ?
                            getEraseDamage(targetInside, (StandEntity) entity) * ((StandEntity) entity).barrageHits : getSpecialDamage(targetInside));
                }
                else {
                    float dmg = entity instanceof SCRapierEntity ? 0 : damageAmount;
                    if (AquaNecklaceHeavyPunch.isASkeleton(getTargetInside()) && entity instanceof SilverChariotEntity) {
                        if (((StandEntity) entity).getCurrentTaskAction() == ModStandsInit.SILVER_CHARIOT_RAPIER_BARRAGE.get()) {
                            dmg *= 0.25F;
                        }
                        else {
                            dmg = 0;
                        }
                    }
                    if (dmg > 0) {
                        DamageUtil.hurtThroughInvulTicks(targetInside, getDamageSource((StandEntityDamageSource) dmgSource), dmg);
                    }
                }
            }
            if (!(dmgSource.isBypassArmor() && dmgSource.isBypassMagic())) {
                if (AquaNecklaceHeavyPunch.isASkeleton(getTargetInside()) && (entity instanceof SCRapierEntity || (entity instanceof SilverChariotEntity &&
                        ((StandEntity) entity).getCurrentTaskAction() == ModStandsInit.SILVER_CHARIOT_RAPIER_BARRAGE.get()))) {
                    damageAmount *= entity instanceof SCRapierEntity ? 1 : 0.75F;
                }
                else {
                    damageAmount /= 4;
                }
            }
        }
        if (dmgSource.isProjectile() && dmgSource.getDirectEntity() != null) {
            Entity projectile = dmgSource.getDirectEntity();
            if (projectile instanceof MRFireballEntity || projectile instanceof MRFlameEntity ||
                    projectile instanceof MRCrossfireHurricaneEntity || projectile instanceof MRRedBindEntity ||
                    projectile instanceof MRDetectorEntity) {
                damageAmount /= 2;
            }
        }
        if (getState() != 2) {
            damageAmount = getState() == 0 ? damageAmount * 0 : damageAmount * 0.1F;
        }
        super.actuallyHurt(dmgSource, damageAmount);
    }

    private static StandEntityDamageSource getDamageSource(StandEntityDamageSource source) {
        StandEntityDamageSource newSource = new StandEntityDamageSource("stand", source.getDirectEntity() != null ? source.getDirectEntity() : null,
                source.getStandPower() != null ? source.getStandPower() : null);
        newSource.setKnockbackReduction(source.getKnockbackFactor());
        if (newSource.preventsDamagingArmor()) {
            newSource.setPreventDamagingArmor();
        }
        return newSource;
    }

    private boolean AttackedByHisStand(Entity directEntity, LivingEntity standUser) {
        if (directEntity instanceof StandEntity && ((StandEntity)directEntity).getUser() == standUser) {
            return true;
        }
        else if (directEntity instanceof DamagingEntity && ((DamagingEntity)directEntity).getOwner() != null){
            LivingEntity owner = ((DamagingEntity)directEntity).getOwner();
            if (owner.isAlive()) {
                return owner instanceof StandEntity && ((StandEntity) owner).getUser() == standUser;
            }
        }
        return false;
    }

    public static float getSpecialDamage(Entity target) {
        float damage = 0;
        boolean PercentDamage = RotpExtraConfig.getCommonConfigInstance(false).PercentDamage.get();
        if (!(target instanceof LivingEntity) || !PercentDamage) {
            damage = StandStatFormulas.getHeavyAttackDamage(8);
            return damage;
        }
        else {
            LivingEntity entity = (LivingEntity) target;
            if (entity.isAlive()) {
                float size = (float) entity.getBoundingBox().getSize();
                float eraseSpace = Math.max(size > 1.09 ? 1 - (size / 5) : 1 - (size - 1), 0.05F);
                damage = entity.getMaxHealth() * ((size > 1.5 ? 0.5F : 0.8F) * eraseSpace);
                return damage * 0.5F;
            }
            return damage;
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSrc) {
        if (getState() == 0 && !(damageSrc.isBypassArmor() && damageSrc.isBypassMagic())) {
            if (damageSrc == DamageSource.OUT_OF_WORLD) {
                return super.isInvulnerableTo(damageSrc);
            }
            return true;
        }
        return super.isInvulnerableTo(damageSrc);
    }

    public boolean canBlockDamage(DamageSource dmgSource) {
        return (getCurrentTaskAction() == InitStands.AQUA_NECKLACE_BLOCK.get() || !isInside()) && super.canBlockDamage(dmgSource);
    }

    @Override
    public void defaultRotation() {
        if (isInside()) {
            if (getTargetInside() != null && !isManuallyControlled() && !isRemotePositionFixed()) {
                setRot(getTargetInside().yRot, getTargetInside().xRot);
            }
            setYHeadRot(this.yRot);
        }
        else {
            super.defaultRotation();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getRemainingFireTicks() > 0) {
            this.setSecondsOnFire(0);
            if (!isInside() || (isInside() && (AquaNecklaceHeavyPunch.isASkeleton(getTargetInside())))) {
                MCUtil.playSound(level, null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.FIRE_EXTINGUISH, this.getSoundSource(), 1.0F, 1.0F, StandUtil::playerCanHearStands);
                if (ClientUtil.canSeeStands()) {
                    ((ServerWorld)level).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 8,
                            this.getBbWidth() / 2F, this.getBbHeight() / 2F, this.getBbWidth() / 2F, 0);
                }
            }
        }
        LivingEntity user = getUser();
        if (user != null && user.level == this.level) {
            double distanceSqr = distanceToSqr(user);
            double rangeSq = getMaxRange();
            rangeSq *= rangeSq;
            if (distanceSqr > rangeSq) {
                setTargetInside(null);
            }
        }
        if (getState() != 2 && (canPassThroughBlocks(this.getDeltaMovement()) || crossingBlocks(true)) && shouldHaveNoPhysics()) {
            updateNoPhysics();
        }
        else {
            updateNoPhysics();
        }
        if (getState() == 1) {
            IStandPower.getStandPowerOptional(getUser()).ifPresent(power -> {
                power.addLearningProgressPoints(InitStands.AQUA_NECKLACE_CHANGE_TO_LIQUID.get(), 0.000056F);
            });
            if (crossingBlocks(false) || isInWater()) {
                setNoGravity(standHasNoGravity());
            }
            else {
                setNoGravity(standHasNoGravity());
            }
        }
        if (getState() == 0 && isInWater()) {
            setState(1);
            IStandPower.getStandPowerOptional(getUser()).ifPresent(stand -> {
                stand.consumeStamina(40);
            });
        }
        if (isInside()) {
            LivingEntity target = getTargetInside();
            if (this.getBoundingBox().getSize() * 1.5D >= target.getBoundingBox().getSize()) {
                StandEntityDamageSource damage = new StandEntityDamageSource("stand", this, getUserPower());
                damage.bypassArmor();
                if (target.isSleeping()) {
                    if (!(target instanceof PlayerEntity || target instanceof VillagerEntity) || AddonInteractionUtil.getLFShrink(target) == 1) {
                        target.stopSleeping();
                    }
                }
                else {
                    DamageUtil.hurtThroughInvulTicks(getTargetInside(), damage.setKnockbackReduction(0), getTargetInside().getMaxHealth());
                }
            }
        }
        boolean canSee = getTargetInside() != null && (AquaNecklaceHeavyPunch.isASkeleton(getTargetInside()));
        if ((isInside() && !canSee) || (getState() == 1 && isInWaterOrRain() && getCurrentTaskAction() != InitStands.AQUA_NECKLACE_GETTING_INTO_ENTITY.get())) {
            this.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(), 2, 0, false, false, false));
            if (user != null && isInside() && !canSee) {
                this.addEffect(new EffectInstance(Effects.BLINDNESS, 21, 0, false, false, false));
            }
        }
        if (user != null && getState() != 2 && crossingBlocks(false)) {
            getUser().addEffect(new EffectInstance(Effects.BLINDNESS, 10, 0, false, false, false));
        }
    }

    @Override
    public void updatePosition() {
        LivingEntity target = getTargetInside();
        if (target != null) {
            if (target.isAlive() && !isBeingRetracted()) {
                Vector3d targetPos = target.position();
                float height = (target.getBbHeight() * 0.6F) * 0.7F;
                float y = height < 0.45F ? 0 : height;
                setPos(targetPos.x, targetPos.y + y, targetPos.z);
            }
            else {
                setTargetInside(null);
                if (!isManuallyControlled())
                    retractStand(false);
            }
        }

        if (!isInside())
            super.updatePosition();
    }

    @Override
    public StandRelativeOffset getDefaultOffsetFromUser() {
        return isArmsOnlyMode() ? offsetDefaultArmsOnly : offsetDefault;
    }

    @Override
    public boolean canBreakBlock(float blockHardness, int blockHarvestLevel) {
        return blockHardness <= 1 && blockHarvestLevel <= 0;
    }

    @Override
    public boolean isFollowingUser() {
        return super.isFollowingUser() && !isInside();
    }

    @Override
    protected boolean shouldHaveNoPhysics() {
//        if (getState() != 2 && (canPassThroughBlocks(this.getDeltaMovement()) || crossingBlocks()) && isManuallyControlled()) {
        if (getState() != 2 && crossingBlocks(true) && isManuallyControlled()) {
            return true;
        }
        return super.shouldHaveNoPhysics();
    }

    @Override
    public void move(MoverType type, Vector3d vec) {
        if (this.noPhysics && getState() != 2) {
            this.setBoundingBox(this.getBoundingBox().move(vec));
            this.setLocationFromBoundingbox();
            LivingEntity user = getUser();
            if (user != null && user.level == this.level) {
                double distanceSqr = distanceToSqr(user);
                double rangeSq = getMaxRange();
                rangeSq *= rangeSq;
                if (distanceSqr > rangeSq) {
                    Vector3d vecToUser = user.position().subtract(position()).scale(1 - rangeSq / distanceSqr);
                    moveWithoutCollision(vecToUser);
                }
                if (!level.isClientSide() && isManuallyControlled() && distanceSqr > 728 && user instanceof PlayerEntity) {
                    double horizontalDistSqr = distanceSqr - Math.pow(getY() - user.getY(), 2);
                    int warningDistance = ((ServerWorld) level).getServer().getPlayerList().getViewDistance() * 16 - 4;
                    if (horizontalDistSqr > warningDistance * warningDistance) {
                        ((PlayerEntity) user).getCapability(PlayerUtilCapProvider.CAPABILITY).ifPresent(cap -> {
                            cap.sendNotification(PlayerUtilCap.OneTimeNotification.HIGH_STAND_RANGE, new TranslationTextComponent("jojo.chat.message.view_distance_stand"));
                        });
                    }
                }
            }
            if (getState() == 1) {
                Vector3d motion = this.getDeltaMovement();
                double y = 0.0D + motion.y;
                boolean fCY = (y > 0.0D && falseCollision(2, false, false)) || (y < 0.0D && falseCollision(2, true, false));
                this.setDeltaMovement(motion.x, fCY ? 0.0D : motion.y, motion.z);
            }
        }
        else {
            super.move(type, vec);
        }
    }

    private void moveWithoutCollision(Vector3d vec) {
        setBoundingBox(getBoundingBox().move(vec));
        setLocationFromBoundingbox();
    }

    private boolean prevTickInput = false;

    @Override
    public void moveStandManually(float strafe, float forward, boolean jumping, boolean sneaking) {
        if (isManuallyControlled() && canMoveManually()) {
            strafe = getManualMovementLocks().strafe(strafe);
            forward = getManualMovementLocks().forward(forward);
            jumping = getManualMovementLocks().up(jumping);
            sneaking = getManualMovementLocks().down(sneaking);
            boolean input = jumping || sneaking || forward != 0 || strafe != 0;
            if (input) {
                double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
                double jump = jumping ? speed : isNoGravity() ? 0 : -0.5D;
                if (sneaking) {
                    jump -= speed;
                    strafe *= 0.5;
                    forward *= 0.5;
                }
                if (crossingBlocks(false)) {
                    jump *= 0.5;
                    strafe *= 0.5;
                    forward *= 0.5;
                }
                if (!prevTickInput) {
                    setDeltaMovement(Vector3d.ZERO);
                }
                else {
                    Vector3d motion = getAbsoluteMotion(new Vector3d((double)strafe, jump, (double)forward), speed, this.yRot)
                            .scale(getUserWalkSpeed() * manualMovementSpeed);
                    setDeltaMovement(motion);
                    if (getState() < 2 && (crossingBlocks(true) || canPassThroughBlocks(motion))) {
                        double x = 0.0D + motion.x;
                        boolean fCX = ((x > 0.0D && falseCollision(1, false, false)) || (x < 0.0D && falseCollision(1, true, false))) &&
                                !((x < 0.0D && falseCollision(1, false, false)) || (x > 0.0D && falseCollision(1, true, false)));
                        double y = 0.0D + motion.y;
                        boolean fCY = (y > 0.0D && falseCollision(2, false, false)) || (y < 0.0D && falseCollision(2, true, false));
                        double z = 0.0D + motion.z;
                        boolean fCZ = ((z > 0.0D && falseCollision(3, false, false)) || (z < 0.0D && falseCollision(3, true, false))) &&
                                !((z < 0.0D && falseCollision(3, false, false)) || (z > 0.0D && falseCollision(3, true, false)));
                        this.setDeltaMovement(fCX ? 0.0D : motion.x, fCY ? 0.0D : motion.y, fCZ ? 0.0D : motion.z);
                    }
                }
            }
            else if (prevTickInput) {
                setDeltaMovement(Vector3d.ZERO);
            }
            prevTickInput = input;
        }
    }

    @Override
    public boolean hadInput() {
        return prevTickInput;
    }

    private static Vector3d getAbsoluteMotion(Vector3d relative, double speed, float facingYRot) {
        double d0 = relative.lengthSqr();
        if (d0 < 1.0E-7D) {
            return Vector3d.ZERO;
        } else {
            Vector3d vec3d = relative.normalize().scale(speed);
            float yRotSin = MathHelper.sin(facingYRot * ((float)Math.PI / 180F));
            float yRotCos = MathHelper.cos(facingYRot * ((float)Math.PI / 180F));
            return new Vector3d(vec3d.x * (double)yRotCos - vec3d.z * (double)yRotSin, vec3d.y, vec3d.z * (double)yRotCos + vec3d.x * (double)yRotSin);
        }
    }

    private boolean falseCollision(int direction, boolean negative, boolean cross) {
        BlockPos pos = this.blockPosition();
        Vector3d vec;
        Vector3d stand = this.position();
        double distance = distanceToSqr(Vector3d.atCenterOf(pos));
        BlockPos pos2;
        Vector3d posE;
        Vector3d vecE;
        double distanceE = distanceToSqr(Vector3d.atCenterOf(pos));
        switch (direction) {
            case 1:
                pos = new BlockPos((negative ? pos.getX() - 1 : pos.getX() + 1), pos.getY(), pos.getZ());
                vec = Vector3d.atCenterOf(pos);
                Vector3d posX = new Vector3d(stand.x, vec.y, vec.z);
                distance = posX.distanceToSqr(vec);
                pos2 = new BlockPos((negative ? pos.getX() - 1 : pos.getX() + 1), this.position().y + 0.76, pos.getZ());
                vecE = Vector3d.atCenterOf(pos2);
                posE = pos2 == pos ? vec : new Vector3d(stand.x, vecE.y, vec.z);
                distanceE = posE.distanceToSqr(vecE);
                break;
            case 2:
                pos = new BlockPos(pos.getX(), (negative ? pos.getY() - 1 : pos.getY() + 1), pos.getZ());
                vec = Vector3d.atCenterOf(pos);
                Vector3d posY = new Vector3d(vec.x, negative ? stand.y : stand.y + 0.76, vec.z);
                distance = posY.distanceToSqr(vec);
                break;
            case 3:
                pos = new BlockPos(pos.getX(), pos.getY(), (negative ? pos.getZ() - 1 : pos.getZ() + 1));
                vec = Vector3d.atCenterOf(pos);
                Vector3d posZ = new Vector3d(vec.x, vec.y, stand.z);
                distance = posZ.distanceToSqr(vec);
                pos2 = new BlockPos(pos.getX(), this.position().y + 0.76,  (negative ? pos.getZ() - 1 : pos.getZ() + 1));
                vecE = Vector3d.atCenterOf(pos2);
                posE = pos2 == pos ? vec : new Vector3d(stand.x, vecE.y, vec.z);
                distanceE = posE.distanceToSqr(vecE);
                break;
        }
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (cross) {
            return listOfTraversableBlocks(block);
        }
        else {
            return (direction != 2 ? distance < 0.9025D || distanceE < 0.9025D : distance < 0.7225D) &&
                    !(listOfTraversableBlocks(block) || state.getCollisionShape(level, pos) == VoxelShapes.empty());
        }
    }

    @Override
    public boolean isPickable() {
        return (getState() != 0 && super.isPickable()) || (getState() == 0 && isInside() && super.isPickable());
    }
}
