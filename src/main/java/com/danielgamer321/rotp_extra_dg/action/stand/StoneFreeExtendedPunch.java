package com.danielgamer321.rotp_extra_dg.action.stand;

import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.SFExtendedPunchEntity;
import com.danielgamer321.rotp_extra_dg.entity.stand.stands.StoneFreeEntity;
import com.danielgamer321.rotp_extra_dg.init.InitStands;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StoneFreeExtendedPunch extends StandEntityAction {

    public StoneFreeExtendedPunch(Builder builder) {
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if (!(power.isActive() && power.getStandManifestation() instanceof StandEntity)) {
            return InitStands.STONE_FREE_USER_STRING_PICK.get();
        }
        else {
            StandEntity stand = (StandEntity) power.getStandManifestation();
            if (stand.isArmsOnlyMode()) {
                return InitStands.STONE_FREE_USER_STRING_PICK.get();
            }
        }
        return super.replaceAction(power, target);
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.STONE_FREE_USER_STRING_PICK.get() };
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if ((stand instanceof StoneFreeEntity && !((StoneFreeEntity) stand).hasForeArm()) && (stand instanceof StoneFreeEntity && !((StoneFreeEntity) stand).hasShortForeArm())) {
            return conditionMessage("stonefree_rightforearm");
        }
        return super.checkStandConditions(stand, power, target);
    }
    
    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        super.onTaskSet(world, standEntity, standPower, phase, task, ticks);
        if (!world.isClientSide()) {
        	StoneFreeEntity stonefree = (StoneFreeEntity) standEntity;
        	SFExtendedPunchEntity rightforearm = new SFExtendedPunchEntity(world, standEntity);
        	standEntity.addProjectile(rightforearm);
        	stonefree.setForeArm(false);
        	stonefree.setShortForeArm(true);
        }
    }
    
    @Override
    protected void onTaskStopped(World world, StandEntity standEntity, IStandPower standPower, StandEntityTask task, @Nullable StandEntityAction newAction) {
        if (!world.isClientSide()) {
            StoneFreeEntity stonefree = (StoneFreeEntity) standEntity;
            takeForeArm(stonefree);
        }
    }
    
    public void takeForeArm(StoneFreeEntity stonefree) {
        stonefree.setForeArm(true);
        stonefree.setShortForeArm(false);
    }
}