package com.danielgamer321.rotp_extra_dg.power.impl.stand.type;

import com.danielgamer321.rotp_extra_dg.action.stand.KraftWorkPlaceProjectile;
import com.danielgamer321.rotp_extra_dg.capability.entity.EntityUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.capability.entity.LivingUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.capability.entity.ProjectileUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.KWItemEntity;
import com.danielgamer321.rotp_extra_dg.init.AddonStands;
import com.danielgamer321.rotp_extra_dg.init.InitEffects;
import com.danielgamer321.rotp_extra_dg.init.InitSounds;
import com.danielgamer321.rotp_extra_dg.init.InitStands;
import com.danielgamer321.rotp_extra_dg.network.PacketManager;
import com.danielgamer321.rotp_extra_dg.network.packets.fromserver.TrSetEntityCanUpdatePacket;
import com.danielgamer321.rotp_extra_dg.network.packets.fromserver.TrSetLockStatusPacket;
import com.danielgamer321.rotp_extra_dg.network.packets.fromserver.TrTagPacket;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.entity.damaging.projectile.CDBlockBulletEntity;
import com.github.standobyte.jojo.entity.damaging.projectile.TommyGunBulletEntity;
import com.github.standobyte.jojo.entity.itemprojectile.BladeHatEntity;
import com.github.standobyte.jojo.entity.itemprojectile.KnifeEntity;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.util.mc.MCUtil;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.danielgamer321.rotp_extra_dg.action.stand.KraftWorkLockYourself.binding;

public class KraftWorkStandType<T extends StandStats> extends EntityStandType<T> {

    public KraftWorkStandType(int color, ITextComponent partName,
            StandAction[] attacks, StandAction[] abilities,
            Class<T> statsClass, T defaultStats, @Nullable StandTypeOptionals additions) {
        super(color, partName, attacks, abilities, statsClass, defaultStats, additions);
    }

    protected KraftWorkStandType(AbstractBuilder<?, T> builder) {
        super(builder);
    }

    @Override
    public float getStaminaRegen(IStandPower power) {
        LivingEntity user = power.getUser();
        if (user.getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> cap.getBlockingItemsStatus()).orElse(false)) {
            return super.getStaminaRegen(power) * 0.5F;
        }
        else {
            return super.getStaminaRegen(power);
        }
    }

    @Override
    public void tickUser(LivingEntity user, IStandPower power) {
        World world = user.level;
        if (!world.isClientSide()) {
            String lock_id = String.valueOf(user.getUUID());
            MCUtil.getAllEntities(world).forEach(entity -> {
                if (entity.getTags().contains(lock_id)) {
                    double distance = entity.distanceToSqr(user);
                    boolean positionLocking = entity.getCapability(EntityUtilCapProvider.CAPABILITY).map(cap -> cap.getPositionLocking()).orElse(false);
                    if (entity instanceof LivingEntity && !(entity instanceof ArmorStandEntity)) {
                        LivingEntity lockedEntity = (LivingEntity) entity;
                        if (!positionLocking || distance > 225 || !lockedEntity.isAlive() || !InitEffects.isLocked(lockedEntity)) {
                            ItemStack helmet = lockedEntity.getItemBySlot(EquipmentSlotType.HEAD);
                            ItemStack chestplace = lockedEntity.getItemBySlot(EquipmentSlotType.CHEST);
                            ItemStack leggings = lockedEntity.getItemBySlot(EquipmentSlotType.LEGS);
                            ItemStack boots = lockedEntity.getItemBySlot(EquipmentSlotType.FEET);
                            lockedEntity.removeEffect(InitEffects.LOCKED_MAIN_HAND.get());
                            lockedEntity.removeEffect(InitEffects.LOCKED_OFF_HAND.get());
                            binding(user, true, helmet);
                            lockedEntity.removeEffect(InitEffects.LOCKED_HELMET.get());
                            binding(user, true, chestplace);
                            lockedEntity.removeEffect(InitEffects.LOCKED_CHESTPLATE.get());
                            binding(user, true, leggings);
                            lockedEntity.removeEffect(InitEffects.LOCKED_LEGGINGS.get());
                            binding(user, true, boots);
                            lockedEntity.removeEffect(InitEffects.LOCKED_POSITION.get());
                            lockedEntity.removeEffect(InitEffects.TRANSPORT_LOCKED.get());
                            lockedEntity.removeEffect(InitEffects.FULL_TRANSPORT_LOCKED.get());
                            setPositionLockingServerSide(entity, false);
                            TagServerSide(lockedEntity, lock_id, false);
                        }
                    }
                    else if (entity instanceof ProjectileEntity) {
                        ProjectileEntity projectile = (ProjectileEntity) entity;
                        int kineticEnergy = entity.getCapability(ProjectileUtilCapProvider.CAPABILITY).map(cap -> cap.getKineticEnergy()).orElse(0);
                        int flightTicks = projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).map(cap -> cap.getFlightTicks()).orElse(0);
                        Vector3d velocity = projectile instanceof FireworkRocketEntity ?
                                projectile.getDeltaMovement().normalize().add(world.random.nextGaussian() * (double)0.0075F * (double)0.0F, world.random.nextGaussian() * (double)0.0075F * (double)0.0F, world.random.nextGaussian() * (double)0.0075F * (double)0.0F).scale((double)3.15F) :
                                projectile.getDeltaMovement().normalize().add(world.random.nextGaussian() * (double)0.0075F * (double)0.0F, world.random.nextGaussian() * (double)0.0075F * (double)0.0F,
                                world.random.nextGaussian() * (double)0.0075F * (double)0.0F).scale((double)Math.min((0.143F * kineticEnergy), 3.15F));
                        if (positionLocking && distance <= 225) {
                            if (projectile.canUpdate()) {
                                setCanUpdateServerSide(projectile, false);
                            }
                            projectile.getDeltaMovement().normalize().add(world.random.nextGaussian() * (double)0.0075F * (double)0.0F, world.random.nextGaussian() * (double)0.0075F * (double)0.0F, world.random.nextGaussian() * (double)0.0075F * (double)0.0F).scale((double)0.001F);
                            projectile.setNoGravity(true);
                        }
                        else {
                            ReleaseProjectile(user, projectile, kineticEnergy, velocity);
                            if (projectile.isNoGravity() && flightTicks > 0) {
                                projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.removePLFlightTicks());
                            }
                            else {
                                projectile.setNoGravity(false);
                                TagServerSide(entity, lock_id, false);
                            }
                        }
                    }
                    else {
                        if (positionLocking && distance <= 225) {
                            entity.fallDistance = 0.0F;
                            setCanUpdateServerSide(entity, false);
                            entity.setNoGravity(true);
                        }
                        else if (!positionLocking || distance > 225 || !user.isAlive()) {
                            setPositionLockingServerSide(entity, false);
                            setCanUpdateServerSide(entity, true);
                            entity.setNoGravity(false);
                            TagServerSide(entity, lock_id, false);
                        }
                    }
                    if (entity.isVehicle()) {
                        for(Entity passengers : entity.getPassengers()) {
                            if (passengers instanceof LivingEntity) {
                                LivingEntity living = (LivingEntity) passengers;
                                IStandPower.getStandPowerOptional(living).ifPresent(userPower -> {
                                    if (IStandPower.getStandPowerOptional(living).map(stand -> !stand.hasPower() ||
                                            stand.getType() != AddonStands.KRAFT_WORK.getStandType()).orElse(false)) {
                                        if (positionLocking && distance <= 225) {
                                            if (!living.hasEffect(InitEffects.TRANSPORT_LOCKED.get())) {
                                                living.addEffect(new EffectInstance(InitEffects.TRANSPORT_LOCKED.get(), 19999980, 0, false, false, true));
                                            }
                                        }
                                        else {
                                            living.removeEffect(InitEffects.TRANSPORT_LOCKED.get());
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });

        }
        super.tickUser(user, power);
    }

    public static void setCanUpdateServerSide(Entity entity, boolean canUpdate) {
        entity.canUpdate(canUpdate);
        if (!entity.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetEntityCanUpdatePacket(entity.getId(), canUpdate), entity);
        }
    }

    public static void setPositionLockingServerSide(Entity target, boolean status) {
        target.getCapability(EntityUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setPositionLocking(status));
        if (!target.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrSetLockStatusPacket(target.getId(), status, true), target);
        }
    }

    public static void TagServerSide(Entity target, String id, boolean purpose) {
        if (purpose) {
            target.addTag(id);
        }
        else {
            target.removeTag(id);
        }
        if (!target.level.isClientSide()) {
            PacketManager.sendToClientsTrackingAndSelf(new TrTagPacket(target.getId(), id, purpose), target);
        }
    }

    public static void ReleaseProjectile(LivingEntity user, ProjectileEntity projectile, int accumulation, Vector3d velocity) {
        World world = projectile.level;
        if (projectile instanceof TommyGunBulletEntity || projectile instanceof CDBlockBulletEntity) {
            replaceProjectile(user, projectile, accumulation);
            projectile.remove();
        }
        if (projectile instanceof AbstractArrowEntity) {
            double increase = 0.0091 * accumulation;
            if (projectile instanceof KWItemEntity) {
                ItemStack item = ((KWItemEntity) projectile).getItem();
                if (item.getItem() instanceof BlockItem) {
                    BlockState blockState = ((BlockItem) item.getItem()).getBlock().defaultBlockState();
                    increase = KraftWorkPlaceProjectile.ineffectiveMaterial(blockState) || KraftWorkPlaceProjectile.ineffectiveItems(item.getItem()) ? increase / 2 :
                            KraftWorkPlaceProjectile.weakMaterial(blockState) && !KraftWorkPlaceProjectile.hardMaterial(blockState) &&
                                    !KraftWorkPlaceProjectile.ineffectiveMaterial(blockState) && !KraftWorkPlaceProjectile.ineffectiveItems(item.getItem()) ? 0 : increase;
                }
                else {
                    increase = KraftWorkPlaceProjectile.ineffectiveItems(item.getItem()) ? increase / 2 : KraftWorkPlaceProjectile.weakItems(item.getItem()) ? 0 : increase;
                }
            }
            ((AbstractArrowEntity) projectile).setBaseDamage(((AbstractArrowEntity) projectile).getBaseDamage() + increase);
        }

        int f;
        if (projectile instanceof BladeHatEntity && accumulation > 3){
            f = accumulation > 5 ? 1 : accumulation / 5;
            world.playSound(null, projectile, ModSounds.BLADE_HAT_THROW.get(), SoundCategory.PLAYERS,
                    1.0F, 0.75F + world.random.nextFloat() * (0.5F * f));
        }
        else if (projectile instanceof TridentEntity && accumulation > 4) {
            f = accumulation > 8 ? 1 : accumulation / 8;
            world.playSound(null, projectile, SoundEvents.TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, f * 1.0F);
        }
        else if (projectile instanceof KnifeEntity && accumulation > 5) {
            f = accumulation > 10 ? 1 : accumulation / 10;
            world.playSound(null, projectile, ModSounds.KNIFE_THROW.get(), SoundCategory.PLAYERS, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + (0.8F)));
        }
        else if (projectile instanceof AbstractArrowEntity && accumulation > 5 && !(projectile instanceof KWItemEntity)) {
            f = accumulation > 20 ? 1 : accumulation / 20;
            world.playSound(null, projectile, SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
        }
        else if (accumulation > 20) {
            f = accumulation > 139 ? 1 : accumulation / 139;
            world.playSound(null, projectile, InitSounds.KRAFT_WORK_RELEASED_PROJECTILE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F / (world.random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
        }
        projectile.setDeltaMovement(velocity);
        setPositionLockingServerSide(projectile, false);
        projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setFlightTicks(accumulation / 20));
        projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setReadyToRelease(false));
        setCanUpdateServerSide(projectile, true);
        IStandPower.getStandPowerOptional(user).ifPresent(stand -> {
            stand.consumeStamina(1);
        });
    }

    public static void replaceProjectile(LivingEntity user, Entity projectile, int kineticEnergy) {
        int KineticEnergy = projectile.getCapability(ProjectileUtilCapProvider.CAPABILITY).map(capa -> capa.getKineticEnergy()).orElse(0);
        if (projectile instanceof CDBlockBulletEntity) {
            CDBlockBulletEntity newBullet = new CDBlockBulletEntity(user, user.level) {
                @Override
                public float getBaseDamage() {
                    return 0.05F * kineticEnergy;
                }
            };
            newBullet.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setKineticEnergy(KineticEnergy));
            newBullet.copyPosition(projectile);
            newBullet.shootFromRotation(projectile, Math.min((0.143F * kineticEnergy), 3.15F), 0.0F);
            projectile.level.addFreshEntity(newBullet);
        }
        else if (projectile instanceof TommyGunBulletEntity) {
            TommyGunBulletEntity newBullet = new TommyGunBulletEntity(user, projectile.level) {
                @Override
                public float getBaseDamage() {
                    return 0.05F * kineticEnergy;
                }
            };
            newBullet.getCapability(ProjectileUtilCapProvider.CAPABILITY).ifPresent(cap -> cap.setKineticEnergy(KineticEnergy));
            newBullet.copyPosition(projectile);
            newBullet.shootFromRotation(projectile, Math.min((0.143F * kineticEnergy), 3.15F), 0.0F);
            projectile.level.addFreshEntity(newBullet);
        }
    }

    private static Vector3d calculateViewVector(float x, float y) {
        float f = x * ((float)Math.PI / 180F);
        float f1 = -y * ((float)Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vector3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }

    public float KWReduceDamageAmount(IStandPower power, float dmgAmount) {
        float damageReductionMult = 0.2F * (InitStands.KRAFT_WORK_BI_STATUS.get()
                .getMaxTrainingPoints(power) >= 1 ? power.getResolveLevel() : power.getResolveLevel() - 1);

        if (damageReductionMult > 0 && getStatus(power)) {
            float damageReduced = dmgAmount * damageReductionMult;
            return dmgAmount - damageReduced;
        }
        else {
            return dmgAmount;
        }
    }

    public boolean getStatus(IStandPower power) {
            LivingEntity user = power.getUser();
        return user.getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> cap.getBlockingItemsStatus()).orElse(false);
    }

    public static class Builder<T extends StandStats> extends AbstractBuilder<Builder<T>, T> {

        @Override
        protected Builder<T> getThis() {
            return this;
        }

        @Override
        public KraftWorkStandType<T> build() {
            return new KraftWorkStandType<>(this);
        }

    }
}