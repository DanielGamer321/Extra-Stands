package com.danielgamer321.rotp_extra_dg.action.stand;

import com.danielgamer321.rotp_extra_dg.capability.entity.LivingUtilCapProvider;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.SFUStringSweepEntity;
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
import net.minecraft.world.World;

import java.util.List;

import static com.danielgamer321.rotp_extra_dg.action.stand.StoneFreeUserBarrier.MaxVarietyOfBarriers;

public class StoneFreeUserStringSweep extends StandAction {

    public StoneFreeUserStringSweep(Builder builder) {
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
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (!world.isClientSide()) {
            MCUtil.playSound(world, null, user, InitSounds.STONE_FREE_STRING.get(),
                    SoundCategory.AMBIENT, 1.0F, 1.0F, StandUtil::playerCanHearStands);
            SFUStringSweepEntity string = new SFUStringSweepEntity(world, user, power);
            world.addFreshEntity(string);
        }
    }

    public static boolean getLandedString(LivingEntity user) {
        List<SFUStringSweepEntity> stringLaunched = user.level.getEntitiesOfClass(SFUStringSweepEntity.class,
                user.getBoundingBox().inflate(16), string -> user.is(string.getOwner()));
        return !stringLaunched.isEmpty() ? true : false;
    }
}