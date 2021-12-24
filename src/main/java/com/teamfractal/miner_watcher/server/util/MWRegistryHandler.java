package com.teamfractal.miner_watcher.server.util;

import com.teamfractal.miner_watcher.server.config.MWServerConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class MWRegistryHandler {
    public static void register(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MWServerConfig.SERVER_CONFIG);
    }
}
