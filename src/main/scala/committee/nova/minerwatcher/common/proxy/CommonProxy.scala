package committee.nova.minerwatcher.common.proxy

import committee.nova.minerwatcher.MinerWatcher.logger
import committee.nova.minerwatcher.common.config.CommonConfig
import committee.nova.minerwatcher.common.event.EventManager
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent): Unit = {
    logger = FMLCommonHandler.instance().getFMLLogger
    new CommonConfig(event)
  }

  def init(event: FMLInitializationEvent): Unit = EventManager.init()
}
