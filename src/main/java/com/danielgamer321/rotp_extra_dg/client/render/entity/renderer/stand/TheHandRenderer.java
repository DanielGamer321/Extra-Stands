package com.danielgamer321.rotp_extra_dg.client.render.entity.renderer.stand;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.client.render.entity.model.stand.TheHandModel;
import com.danielgamer321.rotp_extra_dg.entity.stand.stands.TheHandEntity;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TheHandRenderer extends StandEntityRenderer<TheHandEntity, TheHandModel> {
    
    public TheHandRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpExtraAddon.MOD_ID, "the_hand"), TheHandModel::new),
                new ResourceLocation(RotpExtraAddon.MOD_ID, "textures/entity/stand/the_hand.png"), 0);
    }
}
