package committee.nova.minerwatcher.common.proxy

import committee.nova.minerwatcher.common.config.CommonConfig
import committee.nova.minerwatcher.common.event.EventManager
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.common.MinecraftForge

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent): Unit = new CommonConfig(event)

  def init(event: FMLInitializationEvent): Unit = MinecraftForge.EVENT_BUS.register(new EventManager)
}
