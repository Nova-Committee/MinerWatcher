package com.teamfractal.miner_watcher.server.tools;

import com.teamfractal.miner_watcher.server.MinerWatcher;
import com.teamfractal.miner_watcher.server.config.MWServerConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

import static net.minecraft.util.Util.NIL_UUID;

@Mod.EventBusSubscriber
public class Watcher {
    private static final ITag.INamedTag<Block> CHAT_NOTIFY = BlockTags.bind("chat_notify_blocks");
    private static final ITag.INamedTag<Block> LOG_NOTIFY_1 = BlockTags.bind("log_notify_blocks_1");
    private static final ITag.INamedTag<Block> LOG_NOTIFY_2 = BlockTags.bind("log_notify_blocks_2");
    private static final ITag.INamedTag<Block> LOG_NOTIFY_3 = BlockTags.bind("log_notify_blocks_3");

    @SubscribeEvent
    public static void onBlockBreak(final BlockEvent.BreakEvent event){
        final PlayerEntity player = event.getPlayer();
        final World world = player.level;
        final Block blockBroke = event.getState().getBlock();
        final BlockPos pos = event.getPos();
        final String blockCoord = MessageFormat.format("[{0},{1},{2}]",pos.getX(),pos.getY(),pos.getZ());
        final String blockName = blockBroke.getName().getString();
        final String UUID = player.getStringUUID();
        final String playerName = player.getName().getString();
        sendMessage(world,player,blockBroke,playerName,UUID,blockCoord,blockName);
    }

    private static StringTextComponent generateMessage(boolean exposeName, boolean exposeUUID, String player, String UUID, String blockCoord, String blockName){

        final String nameToExpose = exposeName? player : JsonText.getInstance().get("msg.miner_watcher.someone");
        final String UUIDToExpose = exposeUUID? MessageFormat.format(JsonText.getInstance().get("msg.miner_watcher.uuid"),UUID) : "";
        final String message = JsonText.getInstance().get("msg.miner_watcher.notify");
        return new StringTextComponent(MessageFormat.format(message
                , nameToExpose, UUIDToExpose, blockCoord, blockName));
    }

    private static boolean blockIsInList(Block blockBroke, int event){
        switch (event){
            case 3:
                return LOG_NOTIFY_3.contains(blockBroke);
            case 2:
                return LOG_NOTIFY_2.contains(blockBroke);
            case 1:
                return LOG_NOTIFY_1.contains(blockBroke);
            case 0:
                return CHAT_NOTIFY.contains(blockBroke);
        }
        return false;
    }

    private static void sendMessage(World world, PlayerEntity player, Block blockBroke, String playerName, String UUID, String blockCoord, String blockName){
        if(player instanceof ServerPlayerEntity){
            final boolean isSurvival = ((ServerPlayerEntity) player).gameMode.isSurvival();
            if (MWServerConfig.CHAT_NOTIFY.get() && blockIsInList(blockBroke,0)
                    && (isSurvival || !MWServerConfig.CHAT_NOTIFY_CHECK_SURVIVAL.get())){
                sendToServerPlayer(world,playerName,UUID,blockCoord,blockName);
            }
            if (MWServerConfig.LOG_NOTIFY_1.get() && blockIsInList(blockBroke,1)
                    && (isSurvival || !MWServerConfig.LOG_NOTIFY_1_CHECK_SURVIVAL.get())){
                final int t = MWServerConfig.LOG_NOTIFY_1_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_1_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_1_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,blockCoord,blockName).getString());
            }
            if (MWServerConfig.LOG_NOTIFY_2.get() && blockIsInList(blockBroke,2)
                    && (isSurvival || !MWServerConfig.LOG_NOTIFY_2_CHECK_SURVIVAL.get())){
                final int t = MWServerConfig.LOG_NOTIFY_2_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_2_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_2_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,blockCoord,blockName).getString());
            }
            if (MWServerConfig.LOG_NOTIFY_3.get() && blockIsInList(blockBroke,3)
                    && (isSurvival || !MWServerConfig.LOG_NOTIFY_3_CHECK_SURVIVAL.get())){
                final int t = MWServerConfig.LOG_NOTIFY_3_TYPE.get();
                final boolean eN = MWServerConfig.LOG_NOTIFY_3_EXPOSE_NAME.get();
                final boolean eU = MWServerConfig.LOG_NOTIFY_3_EXPOSE_UUID.get();
                logInEvents(t, generateMessage(eN,eU,playerName,UUID,blockCoord,blockName).getString());
            }
        }
    }


    private static void sendToServerPlayer(World world, String player, String UUID, String blockCoord, String blockName){
        if (!world.isClientSide()){
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null){
                server.getPlayerList().broadcastMessage
                        (generateMessage(MWServerConfig.CHAT_NOTIFY_EXPOSE_NAME.get()
                                        ,MWServerConfig.CHAT_NOTIFY_EXPOSE_UUID.get()
                                        ,player
                                        ,UUID
                                        ,blockCoord
                                        ,blockName)
                                , ChatType.SYSTEM, NIL_UUID);
            }
        }
    }

    private static void logInEvents(int logType, String message){
        Logger logger = MinerWatcher.LOGGER;
        switch (logType) {
            case 4:
                logger.trace(message);
                break;
            case 3 :
                logger.warn(message);
                break;
            case 2 :
                logger.debug(message);
                break;
            case 1 :
                logger.error(message);
                break;
            case 0 :
                logger.info(message);
                break;
        }
    }
}
