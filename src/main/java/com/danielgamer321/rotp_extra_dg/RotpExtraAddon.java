package com.danielgamer321.rotp_extra_dg;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danielgamer321.rotp_extra_dg.init.*;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpExtraAddon.MOD_ID)
public class RotpExtraAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_extra_dg";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpExtraAddon() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RotpExtraConfig.commonSpec);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
