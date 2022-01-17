package com.teamfractal.miner_watcher.server.tools;

import com.google.common.collect.Lists;
import com.teamfractal.miner_watcher.server.MinerWatcher;
import com.teamfractal.miner_watcher.server.config.MWServerConfig;
import com.teamfractal.miner_watcher.server.events.MWAfterBlockBreakEvent;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static net.minecraft.util.Util.NIL_UUID;

public class Watcher {
    private static final Tag<Block> CHAT_NOTIFY = TagFactory.BLOCK.create(new Identifier("chat_notify_blocks"));
    private static final Tag<Block> LOG_NOTIFY_1 = TagFactory.BLOCK.create(new Identifier("log_notify_blocks_1"));
    private static final Tag<Block> LOG_NOTIFY_2 = TagFactory.BLOCK.create(new Identifier("log_notify_blocks_2"));
    private static final Tag<Block> LOG_NOTIFY_3 = TagFactory.BLOCK.create(new Identifier("log_notify_blocks_3"));
    private static final List<ServerPlayerEntity> players = Lists.newArrayList();
    private static MinecraftServer currentServer;

    public static void broadcast(Text message, MessageType type, UUID sender) {
        currentServer.sendSystemMessage(message, sender);

        for (ServerPlayerEntity serverPlayerEntity : players) {
            serverPlayerEntity.sendMessage(message, type, sender);
        }

    }

    private static MinecraftServer getCurrentServer() {
        return currentServer;
    }

    public static void registerEvent() {
        MWAfterBlockBreakEvent.AFTER.register((world, player, block, pos, state, entity) ->
        {
            final String playerName = player.getName().getString();
            final String blockName = block.getName().getString();
            final String blockCoord = MessageFormat.format("[{0},{1},{2}]",pos.getX(),pos.getY(),pos.getZ());
            final String UUID = String.valueOf(player.getUuid());
            sendMessage(world, player, block, playerName, UUID, blockCoord, blockName);
        });
    }

    private static String generateMessage(boolean exposeName, boolean exposeUUID, String player, String UUID, String blockCoord, String blockName){
        final String nameToExpose = exposeName? player : JsonText.getInstance().get("msg.miner_watcher.someone");
        final String UUIDToExpose = exposeUUID? MessageFormat.format(JsonText.getInstance().get("msg.miner_watcher.uuid"),UUID) : "";
        final String message = JsonText.getInstance().get("msg.miner_watcher.notify");
        return MessageFormat.format(message, nameToExpose, UUIDToExpose, blockCoord, blockName);
    }


    private static void sendMessage(World world, PlayerEntity player, Block block, String playerName, String UUID, String pos, String blockName) {
        if(player instanceof ServerPlayerEntity serverPlayer) {
            final boolean isCreative = serverPlayer.isCreative();
            final boolean isSpectator = serverPlayer.isSpectator();
            if (MWServerConfig.CHAT_NOTIFY.get() && blockIsInList(block, 0) && ((!isCreative && !isSpectator) || !MWServerConfig.CHAT_NOTIFY_CHECK_SURVIVAL.get())) {
                sendToServerPlayer(world,playerName,UUID,pos,blockName);
            }
            if (MWServerConfig.LOG_NOTIFY_1.get() && blockIsInList(block, 1) && ((!isCreative && !isSpectator) || !MWServerConfig.LOG_NOTIFY_1_CHECK_SURVIVAL.get())) {
                final int t = MWServerConfig.LOG_NOTIFY_1_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_1_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_1_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,pos,blockName));
            }
            if (MWServerConfig.LOG_NOTIFY_2.get() && blockIsInList(block, 2) && ((!isCreative && !isSpectator) || !MWServerConfig.LOG_NOTIFY_2_CHECK_SURVIVAL.get())) {
                final int t = MWServerConfig.LOG_NOTIFY_2_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_2_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_2_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,pos,blockName));
            }
            if (MWServerConfig.LOG_NOTIFY_3.get() && blockIsInList(block, 3) && ((!isCreative && !isSpectator) || !MWServerConfig.LOG_NOTIFY_3_CHECK_SURVIVAL.get())) {
                final int t = MWServerConfig.LOG_NOTIFY_3_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_3_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_3_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,pos,blockName));
            }
        }
    }

    private static boolean blockIsInList(Block blockBroke, int event){
        switch (event){
            case 3 -> {
                return LOG_NOTIFY_3.contains(blockBroke);
            }
            case 2 -> {
                return LOG_NOTIFY_2.contains(blockBroke);
            }
            case 1 -> {
                return LOG_NOTIFY_1.contains(blockBroke);
            }
            case 0 -> {
                return CHAT_NOTIFY.contains(blockBroke);
            }
        }
        return false;
    }

    private static void sendToServerPlayer(World world, String player, String UUID, String blockCoord, String blockName){
        if (!world.isClient()){
            MinecraftServer server = getCurrentServer();
            if (server != null){
                broadcast(Text.of(generateMessage(MWServerConfig.CHAT_NOTIFY_EXPOSE_NAME.get(), MWServerConfig.CHAT_NOTIFY_EXPOSE_UUID.get(), player, UUID, blockCoord, blockName)), MessageType.SYSTEM, NIL_UUID);
            }
        }
    }

    private static void logInEvents(int logType, String message){
        Logger logger = MinerWatcher.LOGGER;
        switch (logType) {
            case 4 -> logger.trace(message);
            case 3 -> logger.warn(message);
            case 2 -> logger.debug(message);
            case 1 -> logger.error(message);
            case 0 -> logger.info(message);
        }
    }
}
