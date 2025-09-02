package com.danielgamer321.rotp_extra_dg.action.stand;

import com.danielgamer321.rotp_extra_dg.capability.entity.EntityUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.capability.entity.ProjectileUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.KWItemEntity;
import com.danielgamer321.rotp_extra_dg.init.InitSounds;
import com.danielgamer321.rotp_extra_dg.power.impl.stand.type.KraftWorkStandType;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.entity.RoadRollerEntity;
import com.github.standobyte.jojo.entity.damaging.DamagingEntity;
import com.github.standobyte.jojo.entity.damaging.LightBeamEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.*;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.*;
import com.github.standobyte.jojo.entity.itemprojectile.ItemProjectileEntity;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

public class KraftWorkBlock extends StandEntityBlock {

    public KraftWorkBlock(Builder builder) {
        super(builder);
    }

    private boolean AdmittedProjectiles(Entity projectile) {
        return (projectile instanceof FishingBobberEntity || projectile instanceof AbstractArrowEntity ||
                projectile instanceof DamagingProjectileEntity || projectile instanceof ThrowableEntity ||
                projectile instanceof DamagingEntity) && !(projectile instanceof SnowballEntity ||
                projectile instanceof EggEntity || projectile instanceof SmallFireballEntity ||
                projectile instanceof WitherSkullEntity || projectile instanceof DragonFireballEntity ||
                projectile instanceof HamonCutterEntity || projectile instanceof HamonTurquoiseBlueOverdriveEntity ||
                projectile instanceof HamonBubbleBarrierEntity || projectile instanceof HamonBubbleEntity ||
                projectile instanceof HamonBubbleCutterEntity || projectile instanceof PillarmanDivineSandstormEntity ||
                projectile instanceof LightBeamEntity || projectile instanceof MRFireballEntity ||
                projectile instanceof MRFlameEntity || projectile instanceof MRCrossfireHurricaneEntity);
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (userPower.getResolveLevel() >= 1) {
            LivingEntity user = userPower.getUser();
            world.getEntitiesOfClass(Entity.class, standEntity.getBoundingBox().inflate(standEntity.getAttributeValue(ForgeMod.REACH_DISTANCE.get())),
                    entity -> standEntity.getLookAngle().dot(entity.getDeltaMovement().reverse().normalize()) >= MathHelper.cos((float) (30.0 +
                            MathHelper.clamp(standEntity.getPrecision(), 0, 16) * 30.0 / 16.0) * MathUtil.DEG_TO_RAD)).forEach(entity -> {
                double distance = standEntity.getBoundingBox().getCenter().distanceToSqr(entity.position());
                if (userPower.getResolveLevel() >= 2 && !user.isShiftKeyDown()) {
                    boolean PositionLocking = entity.getCapability(EntityUtilCapProvider.CAPABILITY).map(cap -> cap.getPositionLocking()).orElse(false);
                    if (PositionLocking && entity instanceof ProjectileEntity) {
                        if (entity.getCapability(ProjectileUtilCapProvider.CAPABILITY).map(cap -> cap.getConfirmation()).orElse(false)) {
                            entity.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setConfirmation(false));
                            IStandPower.getStandPowerOptional(user).ifPresent(stand -> {
                                stand.consumeStamina(9.5F);
                            });
                        }
                    }
                    else if (!PositionLocking) {
                        if (entity instanceof BoatEntity || entity instanceof AbstractMinecartEntity ||
                                entity instanceof TNTEntity || entity instanceof FallingBlockEntity ||
                                entity instanceof EyeOfEnderEntity || entity instanceof ArmorStandEntity ||
                                entity instanceof RoadRollerEntity) {
                            if (user.distanceToSqr(entity) < entity.getDeltaMovement().lengthSqr() * 34) {
                                LockTarget(user, entity);
                            }
                        }
                        else if (entity instanceof ProjectileEntity && AdmittedProjectiles(entity)) {
                            ProjectileEntity projectile = (ProjectileEntity) entity;
                            if (distance < 5.5 && entity instanceof ModdedProjectileEntity &&
                                    ((ModdedProjectileEntity) entity).standDamage() && !(entity instanceof OwnerBoundProjectileEntity)) {
                                deflectProjectile(entity, standEntity, user);
                                MCUtil.playSound(world, null, user, InitSounds.KRAFT_WORK_BLOCKS_A_PROJECTILE.get(),
                                        SoundCategory.AMBIENT, 1.0F, 1.0F, StandUtil::playerCanHearStands);
                            }
                            if (distance < 9 - (4 - (4 * (Math.min(projectile.getDeltaMovement().lengthSqr(), 3.15) / 3.15)))) {
                                if (!(entity instanceof ModdedProjectileEntity &&
                                        ((ModdedProjectileEntity) projectile).standDamage()) && projectile.getOwner() != user) {
                                    if (userPower.getResolveLevel() >= 3) {
                                        projectile.shootFromRotation(user, user.xRot, user.yRot, 0.0F, 0.001F, 1.0F);
                                    }
                                    if (!(projectile instanceof EnderPearlEntity || (projectile instanceof ItemProjectileEntity &&
                                            !(projectile instanceof KWItemEntity)))) {
                                        projectile.setOwner(user);
                                    }
                                    if (projectile instanceof OwnerBoundProjectileEntity) {
                                        blockBound((OwnerBoundProjectileEntity) projectile);
                                    }
                                    else {
                                        LockTarget(user, projectile);
                                        projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setConfirmation(true));
                                        projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setKineticEnergy(0));
                                        world.playSound(null, standEntity, InitSounds.KRAFT_WORK_BLOCKS_A_PROJECTILE.get(), SoundCategory.AMBIENT,
                                                1.0F, 1.0F + (world.random.nextFloat() - 0.5F) * 0.15F);
                                    }
                                }
                            }
                        }
                    }
                }
                else if (entity instanceof ProjectileEntity && AdmittedProjectiles(entity)) {
                    if (distance < 5.5 && entity instanceof ModdedProjectileEntity &&
                            ((ModdedProjectileEntity) entity).standDamage() && !(entity instanceof OwnerBoundProjectileEntity)) {
                        deflectProjectile(entity, standEntity, user);
                        MCUtil.playSound(world, null, user, InitSounds.KRAFT_WORK_BLOCKS_A_PROJECTILE.get(),
                                SoundCategory.AMBIENT, 1.0F, 1.0F, StandUtil::playerCanHearStands);
                    }
                    else if (distance < 5.5 && entity instanceof OwnerBoundProjectileEntity) {
                        blockBound((OwnerBoundProjectileEntity) entity);
                    }
                }
            });
        }
    }

    private void blockBound(OwnerBoundProjectileEntity bound) {
        if (bound instanceof SatiporojaScarfEntity || bound instanceof ZoomPunchEntity ||
                bound instanceof PillarmanHornEntity || bound instanceof PillarmanRibEntity ||
                bound instanceof PillarmanVeinEntity) {
            if (bound instanceof SatiporojaScarfEntity) {
                bound.remove();
            }
            else {
                bound.tickCount = bound instanceof PillarmanHornEntity ? 27 : bound.ticksLifespan() / 2;
            }
        }
    }

    public void LockTarget(LivingEntity user, Entity target) {
        String lock_id = String.valueOf(user.getUUID());
        KraftWorkStandType.setPositionLockingServerSide(target, true);
        KraftWorkStandType.TagServerSide(target, lock_id, true);
        KraftWorkStandType.setCanUpdateServerSide(target, false);
        IStandPower.getStandPowerOptional(user).ifPresent(stand -> {
            stand.consumeStamina(10);
        });
    }

    private boolean deflectProjectile(Entity projectile, StandEntity stand, LivingEntity user) {
        if (projectile == null || projectile instanceof ModdedProjectileEntity && !((ModdedProjectileEntity) projectile).canBeDeflected(stand)) return false;

        JojoModUtil.deflectProjectile(projectile, stand.getLookAngle().normalize().scale(projectile.getDeltaMovement().length()), projectile.position());
        IStandPower.getStandPowerOptional(user).ifPresent(power -> {
            power.consumeStamina(0.5F);
        });
        return true;
    }
}
