package com.danielgamer321.rotp_extra_dg.util;

import com.danielgamer321.rotp_extra_dg.RotpExtraAddon;

import com.github.standobyte.jojo.capability.entity.LivingUtilCapProvider;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Comparator;
import java.util.stream.Stream;

@EventBusSubscriber(modid = RotpExtraAddon.MOD_ID)
public class AddonInteractionUtil {
    public static final ResourceLocation LF_SHRINK = new ResourceLocation("rotp_littlefeet", "lf_shrink_target");
    public static final ResourceLocation WR_BLEEDING = new ResourceLocation("rotp_wr", "bleeding");
    public static final ResourceLocation MIH_BLEEDING = new ResourceLocation("rotp_mih", "bleeding");
    public static final ResourceLocation KC_BLEEDING = new ResourceLocation("rotp_kingcrimson", "bleeding");
    public static final ResourceLocation CREAM_BLEEDING = new ResourceLocation("rotp_cream", "bleeding");
    public static final ResourceLocation KC_GAPING_WOUND = new ResourceLocation("rotp_kingcrimson", "gaping_wound");
    public static final ResourceLocation KC_MANGLED_BODY = new ResourceLocation("rotp_kingcrimson", "mangled_body");
    public static final ResourceLocation CM_INVERSION = new ResourceLocation("rotp_cm", "cm_inversion");
    public static float getLFShrink(LivingEntity target) {
        boolean Shrink = target.getCapability(LivingUtilCapProvider.CAPABILITY).resolve().map(cap -> cap.getEffectsTargetedBy().stream()
                        .filter(effect -> LF_SHRINK.equals(effect.effectType.getRegistryName()))
                        .map(effect -> effect.effectType.getRegistryName().equals(LF_SHRINK)))
                .orElse(Stream.empty()).findAny().isPresent();
        return Shrink ? 1 : 0;
    }

    public static float getWRBleeding(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (WR_BLEEDING.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getMIHBleeding(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (MIH_BLEEDING.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getKCBleeding(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (KC_BLEEDING.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getCreamBleeding(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (CREAM_BLEEDING.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getGapingWound(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (KC_GAPING_WOUND.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getMangledBody(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (KC_MANGLED_BODY.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    public static float getInvestedEntity(LivingEntity entity) {
        return Math.max(entity.getActiveEffectsMap().entrySet().stream().map(entry -> {
            if (CM_INVERSION.equals(entry.getKey().getRegistryName())) {
                return entry.getValue().getAmplifier() + 1F;
            }
            return 0F;
        }).max(Comparator.naturalOrder()).orElse(0F), 0);
    }

    private static final ResourceLocation SCALPEL = new ResourceLocation("rotp_metallica", "scalpel");
    private static final ResourceLocation EMPEROR = new ResourceLocation("rotp_zemperor", "emperor");
    private static final ResourceLocation CREAM_STARTER = new ResourceLocation("rotp_zcs", "cream_starter");
    private static final ResourceLocation CTR_MASK = new ResourceLocation("rotp_ctr", "catch_the_rainbow_mask_item");
    public static boolean isScalpel(ItemStack item) {
        return SCALPEL.equals(item.getItem().getRegistryName());
    }

    public static boolean isStandItem(ItemStack item) {
        return EMPEROR.equals(item.getItem().getRegistryName()) ||
                CREAM_STARTER.equals(item.getItem().getRegistryName()) ||
                CTR_MASK.equals(item.getItem().getRegistryName());
    }
}
