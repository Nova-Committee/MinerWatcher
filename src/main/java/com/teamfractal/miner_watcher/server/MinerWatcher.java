package com.teamfractal.miner_watcher.server;

import com.teamfractal.miner_watcher.server.util.MWRegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(MinerWatcher.MODID)
public class MinerWatcher
{
    public static final String MODID = "miner_watcher";
    public static final Logger LOGGER = LogManager.getLogger();

    public MinerWatcher() {
        MWRegistryHandler.register();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Miner Watcher activated!");
    }
}
