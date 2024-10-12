package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.damaging.extending;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.client.render.entity.model.ownerbound.repeating.SFStringModel;
import com.github.standobyte.jojo.client.render.entity.renderer.damaging.extending.ExtendingEntityRenderer;
import com.github.standobyte.jojo.entity.damaging.projectile.ownerbound.OwnerBoundProjectileEntity;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public abstract class SFStringAbstractRenderer<T extends OwnerBoundProjectileEntity> extends ExtendingEntityRenderer<T, SFStringModel<T>> {
    
    public SFStringAbstractRenderer(EntityRendererManager renderManager, SFStringModel<T> model) {
        super(renderManager, model, new ResourceLocation(RotpExtraAddon.MOD_ID, "textures/entity/projectiles/sf_strings.png"));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), texPath);
    }
}
