package committee.nova.minerwatcher

import committee.nova.minerwatcher.common.proxy.CommonProxy
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.Logger

@Mod(modid = MinerWatcher.MODID, useMetadata = true, acceptableRemoteVersions = "*", modLanguage = "scala")
object MinerWatcher {
  final val VERSION = "1.0"
  final val MODID = "miner_watcher"
  final val cp = "committee.nova.minerwatcher.common.proxy.CommonProxy"
  @SidedProxy(serverSide = cp, clientSide = cp)
  var proxy: CommonProxy = _
  var logger: Logger = _

  @EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)
}