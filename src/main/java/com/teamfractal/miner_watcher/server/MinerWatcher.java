package com.teamfractal.miner_watcher.server;

import com.teamfractal.miner_watcher.server.config.MWServerConfig;
import com.teamfractal.miner_watcher.server.tools.Watcher;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinerWatcher implements DedicatedServerModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("MinerWatcher");

	@Override
	public void onInitializeServer() {
		LOGGER.info("Miner Watcher activated!");
		ModLoadingContext.registerConfig("miner_watcher", ModConfig.Type.COMMON, MWServerConfig.SERVER_CONFIG);
		Watcher.registerEvent();
	}
}
