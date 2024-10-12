package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.damaging.projectile;

import com.danielgamer321.rotp_extra_dg.entity.damaging.projectile.KWItemEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;

public class KWItemRenderer extends SpriteRenderer<KWItemEntity> {

    public KWItemRenderer(EntityRendererManager renderManager) {
        super(renderManager, Minecraft.getInstance().getItemRenderer(), 1F, true);
    }
}
