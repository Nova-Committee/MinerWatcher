package committee.nova.minerwatcher

import committee.nova.minerwatcher.MinerWatcher.{logger, proxy}
import committee.nova.minerwatcher.common.proxy.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import org.apache.logging.log4j.Logger

object MinerWatcher {
  final val VERSION = "1.0"
  final val MODID = "miner_watcher"
  @SidedProxy(serverSide = "committee.nova.minerwatcher.common.proxy.CommonProxy")
  val proxy: CommonProxy = new CommonProxy
  @Mod.Instance(MinerWatcher.MODID)
  var instance: MinerWatcher = _
  var logger: Logger = _
}

@Mod(modid = MinerWatcher.MODID, useMetadata = true)
class MinerWatcher {
  MinerWatcher.instance = this
  logger = FMLCommonHandler.instance().getFMLLogger

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)
}