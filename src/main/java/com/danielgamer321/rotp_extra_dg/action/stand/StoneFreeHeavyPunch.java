package com.danielgamer321.rotp_extra_dg.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandRelativeOffset;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class StoneFreeHeavyPunch extends StandEntityHeavyAttack {

    public StoneFreeHeavyPunch(Builder builder) {
        super(builder);
    }

    public double getSlideDistance() {
        return 2.75;
    }

    @Override
    public void standTickWindup(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        int ticksLeft = task.getTicksLeft();
        if (ticksLeft == 2) {
            Vector3d targetPos = task.getTarget().getTargetPos(true);
            Vector3d slideVec;
            if (targetPos != null) {
                slideVec = targetPos.subtract(standEntity.getEyePosition(1.0F));
                slideVec = slideVec.normalize().scale(MathHelper.clamp(slideVec.length() - standEntity.getBbWidth(), 0, getSlideDistance()));
            }
            else {
                slideVec = standEntity.getLookAngle().scale(getSlideDistance());
            }
            standEntity.setDeltaMovement(slideVec);
        }
        else if (ticksLeft == 1) {
            standEntity.setDeltaMovement(Vector3d.ZERO);
        }
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        double strength = stand.getAttackDamage();
        return super.punchEntity(stand, target, dmgSource)
                .addKnockback(0.5F + (float) strength / 8);
    }

    @Override
    public StandRelativeOffset getOffsetFromUser(IStandPower standPower, StandEntity standEntity, StandEntityTask task) {
        return StandRelativeOffset.noYOffset(-0.75, -0.75);
    }

    @Override
    protected boolean standMovesByItself(IStandPower standPower, StandEntity standEntity) {
        Phase phase = standEntity.getCurrentTaskPhase().get();
        return phase == Phase.WINDUP && standEntity.getCurrentTask().map(StandEntityTask::getTicksLeft).get() <= 2
                || phase == Phase.PERFORM || phase == Phase.RECOVERY;
    }
}
