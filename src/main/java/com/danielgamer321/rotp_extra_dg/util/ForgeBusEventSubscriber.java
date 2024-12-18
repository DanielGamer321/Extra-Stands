package com.danielgamer321.rotp_extra_dg.util;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;
import com.danielgamer321.rotp_extra_dg.capability.entity.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = RotpExtraAddon.MOD_ID)
public class ForgeBusEventSubscriber {
    private static final ResourceLocation LIVING_UTIL_CAP = new ResourceLocation(RotpExtraAddon.MOD_ID, "living_util");
    private static final ResourceLocation ENTITY_UTIL_CAP = new ResourceLocation(RotpExtraAddon.MOD_ID, "entity_util");
    private static final ResourceLocation PROJECTIL_UTIL_CAP = new ResourceLocation(RotpExtraAddon.MOD_ID, "projectile_util");

    
    @SubscribeEvent
    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        event.addCapability(ENTITY_UTIL_CAP, new EntityUtilCapProvider(entity));
        if (entity instanceof LivingEntity) {
            event.addCapability(LIVING_UTIL_CAP, new LivingUtilCapProvider((LivingEntity) entity));
        }
        if (entity instanceof ProjectileEntity) {
            event.addCapability(PROJECTIL_UTIL_CAP, new ProjectileUtilCapProvider(entity));
        }
    }
    
    public static void registerCapabilities() {
        CapabilityManager.INSTANCE.register(LivingUtilCap.class, new LivingUtilCapStorage(), () -> new LivingUtilCap(null));
        CapabilityManager.INSTANCE.register(EntityUtilCap.class, new EntityUtilCapStorage(), () -> new EntityUtilCap(null));
        CapabilityManager.INSTANCE.register(ProjectileUtilCap.class, new ProjectileUtilCapStorage(), () -> new ProjectileUtilCap(null));
    }
}
