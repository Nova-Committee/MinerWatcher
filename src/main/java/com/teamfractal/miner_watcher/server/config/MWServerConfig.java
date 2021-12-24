package com.teamfractal.miner_watcher.server.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MWServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec.ConfigValue<String> LANGUAGE;
    public static ForgeConfigSpec.BooleanValue CHAT_NOTIFY;
    public static ForgeConfigSpec.BooleanValue CHAT_NOTIFY_EXPOSE_NAME;
    public static ForgeConfigSpec.BooleanValue CHAT_NOTIFY_EXPOSE_UUID;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_1;
    public static ForgeConfigSpec.IntValue LOG_NOTIFY_1_TYPE;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_1_EXPOSE_NAME;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_1_EXPOSE_UUID;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_2;
    public static ForgeConfigSpec.IntValue LOG_NOTIFY_2_TYPE;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_2_EXPOSE_NAME;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_2_EXPOSE_UUID;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_3;
    public static ForgeConfigSpec.IntValue LOG_NOTIFY_3_TYPE;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_3_EXPOSE_NAME;
    public static ForgeConfigSpec.BooleanValue LOG_NOTIFY_3_EXPOSE_UUID;

    static{
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        SERVER_BUILDER.comment("Miner Watcher's notification configuration").push("Notification settings");
        LANGUAGE = SERVER_BUILDER.comment("","What language should be used?", "There should be a json file in assets/miner_watcher/lang whose name is the same to your value here")
                .define("language", "en_us");
        CHAT_NOTIFY = SERVER_BUILDER.comment("","Should specific block mining be notified in chat?")
                .define("chat_notify",true);
        CHAT_NOTIFY_EXPOSE_NAME = SERVER_BUILDER.comment("","Should miner's name in specific block mining event be notified in chat?")
                .define("chat_notify_expose_name", true);
        CHAT_NOTIFY_EXPOSE_UUID = SERVER_BUILDER.comment("","Should miner's UUID in specific block mining event be notified in chat?")
                .define("chat_notify_expose_uuid", false);
        LOG_NOTIFY_1 = SERVER_BUILDER.comment("","Should specific block mining be notified in log event 1?")
                .define("log_notify_1",true);
        LOG_NOTIFY_1_TYPE = SERVER_BUILDER.comment("","What type should be the notification in log event 1 of?","0:INFO, 1:ERROR, 2:DEBUG, 3:WARN, 4:TRACE")
                .defineInRange("log_event_1_type",3,0,4);
        LOG_NOTIFY_1_EXPOSE_NAME = SERVER_BUILDER.comment("","Should miner's name in specific block mining event be notified in log event 1?")
                .define("log_notify_1_expose_name", true);
        LOG_NOTIFY_1_EXPOSE_UUID = SERVER_BUILDER.comment("","Should miner's UUID in specific block mining event be notified in log event 1?")
                .define("log_notify_1_expose_uuid", true);
        LOG_NOTIFY_2 = SERVER_BUILDER.comment("","Should specific block mining be notified in log event 2?")
                .define("log_notify_2",false);
        LOG_NOTIFY_2_TYPE = SERVER_BUILDER.comment("","What type should be the notification in log event 2 of?","0:INFO, 1:ERROR, 2:DEBUG, 3:WARN, 4:TRACE")
                .defineInRange("log_event_2_type",0,0,4);
        LOG_NOTIFY_2_EXPOSE_NAME = SERVER_BUILDER.comment("","Should miner's name in specific block mining event be notified in log event 2?")
                .define("log_notify_2_expose_name", true);
        LOG_NOTIFY_2_EXPOSE_UUID = SERVER_BUILDER.comment("","Should miner's UUID in specific block mining event be notified in log event 2?")
                .define("log_notify_2_expose_uuid", true);
        LOG_NOTIFY_3 = SERVER_BUILDER.comment("","Should specific block mining be notified in log event 3?")
                .define("log_notify_3",false);
        LOG_NOTIFY_3_TYPE = SERVER_BUILDER.comment("","What type should be the notification in log event 3 of?","0:INFO, 1:ERROR, 2:DEBUG, 3:WARN, 4:TRACE")
                .defineInRange("log_event_3_type",0,0,4);
        LOG_NOTIFY_3_EXPOSE_NAME = SERVER_BUILDER.comment("","Should miner's name in specific block mining event be notified in log event 3?")
                .define("log_notify_3_expose_name", true);
        LOG_NOTIFY_3_EXPOSE_UUID = SERVER_BUILDER.comment("","Should miner's UUID in specific block mining event be notified in log event 3?")
                .define("log_notify_3_expose_uuid", true);

        SERVER_BUILDER.pop();
        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
