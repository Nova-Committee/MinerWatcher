package committee.nova.minerwatcher.common.config

import committee.nova.minerwatcher.common.config.CommonConfig._
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.config.Configuration
import org.apache.logging.log4j.Logger

object CommonConfig {
  var logger: Logger = _
  var config: Configuration = _
  var chatList: Array[String] = _
  var logList: Array[String] = _
  var warnList: Array[String] = _
}

class CommonConfig(event: FMLPreInitializationEvent) {
  logger = event.getModLog
  config = new Configuration(event.getSuggestedConfigurationFile)
  config.load()
  chatList = config.getStringList("chatList", Configuration.CATEGORY_GENERAL, Array("oreDiamond", "exampleBlock1", "exampleBlock2"),
    "When a player mines these blocks, there will be a chat notification.")
  logList = config.getStringList("logList", Configuration.CATEGORY_GENERAL, Array("oreIron", "exampleBlock1", "exampleBlock2"),
    "When a player mines these blocks, there will be a log info notification.")
  warnList = config.getStringList("warnList", Configuration.CATEGORY_GENERAL, Array("oreDiamond", "exampleBlock1", "exampleBlock2"),
    "When a player mines these blocks, there will be a log warn notification.")
  config.save()
}
