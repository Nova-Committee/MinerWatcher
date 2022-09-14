package committee.nova.minerwatcher.common.event

import com.ibm.icu.text.MessageFormat
import committee.nova.minerwatcher.MinerWatcher
import committee.nova.minerwatcher.common.config.CommonConfig
import committee.nova.minerwatcher.common.config.CommonConfig._
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.ChatComponentText
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.BlockEvent.BreakEvent

object EventManager {
  def init(): Unit = MinecraftForge.EVENT_BUS.register(new EventManager)
}

class EventManager {
  @SubscribeEvent
  def onBlockBreak(event: BreakEvent): Unit = {
    val player = event.getPlayer
    if (player == null) return
    val block = event.block
    if (block == null) return
    val unlocalized = block.getUnlocalizedName.substring(5)
    val msg = MessageFormat.format(CommonConfig.notification, player.getDisplayName, s"(${event.x}, ${event.y}, ${event.z})", block.getLocalizedName)
    if (logList.contains(unlocalized)) MinerWatcher.logger.info(msg)
    if (warnList.contains(unlocalized)) MinerWatcher.logger.warn(msg)
    if (chatList.contains(unlocalized)) {
      val list = MinecraftServer.getServer.getConfigurationManager.playerEntityList
      val size = list.size()
      for (i <- 0 until size) {
        list.get(i) match {
          case player1: EntityPlayer => player1.addChatComponentMessage(new ChatComponentText(msg))
          case _ =>
        }
      }
    }
  }
}
