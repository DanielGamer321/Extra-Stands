package com.danielgamer321.rotp_extra_dg.action.stand;

import com.danielgamer321.rotp_extra_dg.capability.entity.LivingUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.SFUStringSpikeEntity;
import com.danielgamer321.rotp_extra_dg.init.InitSounds;
import com.danielgamer321.rotp_extra_dg.power.impl.stand.type.StoneFreeStandType;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import static com.danielgamer321.rotp_extra_dg.action.stand.StoneFreeUserBarrier.MaxVarietyOfBarriers;

public class StoneFreeUserStringPick extends StandAction {

    public StoneFreeUserStringPick(Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (((StoneFreeStandType<?>) power.getType()).getPlacedBarriersCount(power) >= MaxVarietyOfBarriers(user)) {
            return conditionMessage("string_limit");
        }
        if (!user.getCapability(LivingUtilCapProvider.CAPABILITY).map(cap -> cap.userString(user)).orElse(null)) {
            return ActionConditionResult.NEGATIVE;
        }
        return super.checkSpecificConditions(user, power, target);
    }

    @Override
    public void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            MCUtil.playSound(world, null, user, InitSounds.STONE_FREE_STRING.get(),
                    SoundCategory.AMBIENT, 1.0F, 1.0F, StandUtil::playerCanHearStands);
            SFUStringSpikeEntity string = new SFUStringSpikeEntity(world, user, power);
            world.addFreshEntity(string);
        }
    }

    @Override
    protected boolean autoSummonStand(IStandPower power) {
        LivingEntity user = power.getUser();
        return super.autoSummonStand(power) || user.getHealth() <= 5;
    }

    @Override
    public int getHoldDurationToFire(IStandPower power) {
        int hold = super.getHoldDurationToFire(power);
        return shortedHoldDuration(power, super.getHoldDurationToFire(power));
    }

    private int shortedHoldDuration(IStandPower power, int ticks) {
        LivingEntity user = power.getUser();
        int hold = 40;
        if (!power.isUserCreative() && power.getUser() != null) {
            hold = MathHelper.ceil((float) hold / (8 * (user.getMaxHealth() / user.getHealth())));
        }
        return ticks > 0 && user.getHealth() < user.getMaxHealth() ? hold : ticks;
    }

    public static boolean getLandedString(LivingEntity user) {
        List<SFUStringSpikeEntity> stringLaunched = user.level.getEntitiesOfClass(SFUStringSpikeEntity.class,
                user.getBoundingBox().inflate(16), string -> user.is(string.getOwner()));
        return !stringLaunched.isEmpty() ? true : false;
    }
}
