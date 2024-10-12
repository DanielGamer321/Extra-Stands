package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.damaging.extending;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.client.render.entity.model.ownerbound.repeating.SFUStringSpikeModel;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.SFUStringSpikeEntity;
import com.github.standobyte.jojo.client.render.entity.renderer.damaging.extending.ExtendingEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SFUStringSpikeRenderer extends ExtendingEntityRenderer<SFUStringSpikeEntity, SFUStringSpikeModel> {

    public SFUStringSpikeRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SFUStringSpikeModel(), new ResourceLocation(RotpExtraAddon.MOD_ID, "textures/entity/projectiles/sf_strings.png"));
    }

    @Override
    public ResourceLocation getTextureLocation(SFUStringSpikeEntity entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), texPath);
    }

}
