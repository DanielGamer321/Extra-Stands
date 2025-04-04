package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.damaging.extending;

import com.danielgamer321.rotp_extra_dg.client.render.entity.model.ownerbound.repeating.SFStringModel;
import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.ownerbound.SFUStringWhipEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class SFUStringWhipRenderer extends SFStringAbstractRenderer<SFUStringWhipEntity> {

    public SFUStringWhipRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SFStringModel<SFUStringWhipEntity>());
    }
}
