package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.stand;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.client.render.entity.model.stand.KraftWorkModel;
import com.danielgamer321.rotp_extra_dg.entity.stand.stands.KraftWorkEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class KraftWorkRenderer extends StandEntityRenderer<KraftWorkEntity, KraftWorkModel> {

    public KraftWorkRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpExtraAddon.MOD_ID, "kraft_work"), KraftWorkModel::new),
                new ResourceLocation(RotpExtraAddon.MOD_ID, "textures/entity/stand/kraft_work.png"), 0);
    }
}
