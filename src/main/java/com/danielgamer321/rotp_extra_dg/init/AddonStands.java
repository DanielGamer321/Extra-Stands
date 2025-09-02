package com.danielgamer321.rotp_extra_dg.init;

import com.danielgamer321.rotp_extra_dg.entity.stand.stands.*;
import com.danielgamer321.rotp_extra_dg.power.impl.stand.type.*;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<AquaNecklaceEntity>>
    AQUA_NECKLACE = new EntityStandSupplier<>(InitStands.STAND_AQUA_NECKLACE);

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<TheHandEntity>>
    THE_HAND = new EntityStandSupplier<>(InitStands.STAND_THE_HAND);

    public static final EntityStandSupplier<KraftWorkStandType<StandStats>, StandEntityType<KraftWorkEntity>>
    KRAFT_WORK = new EntityStandSupplier<>(InitStands.STAND_KRAFT_WORK);

    public static final EntityStandSupplier<StoneFreeStandType<StandStats>, StandEntityType<StoneFreeEntity>>
    STONE_FREE = new EntityStandSupplier<>(InitStands.STAND_STONE_FREE);
}
