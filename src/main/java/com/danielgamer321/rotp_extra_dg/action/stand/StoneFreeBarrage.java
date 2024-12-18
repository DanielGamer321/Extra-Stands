package com.danielgamer321.rotp_extra_dg.action.stand;

import com.danielgamer321.rotp_extra_dg.init.InitStands;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

public class StoneFreeBarrage extends StandEntityMeleeBarrage {

    public StoneFreeBarrage(Builder builder) {
        super(builder);
    }

    @Override
    protected Action<IStandPower> replaceAction(IStandPower power, ActionTarget target) {
        if (!(power.isActive() && power.getStandManifestation() instanceof StandEntity)) {
            return InitStands.STONE_FREE_USER_STRING_SWEEP.get();
        }
        else {
            StandEntity stand = (StandEntity) power.getStandManifestation();
            if (stand.isArmsOnlyMode()) {
                return InitStands.STONE_FREE_USER_STRING_SWEEP.get();
            }
        }
        return super.replaceAction(power, target);
    }

    @Override
    public StandAction[] getExtraUnlockable() {
        return new StandAction[] { InitStands.STONE_FREE_USER_STRING_SWEEP.get(), InitStands.STONE_FREE_USER_STRING_WHIP.get() };
    }
}
