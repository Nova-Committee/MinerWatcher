package com.teamfractal.miner_watcher.server.tools;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.teamfractal.miner_watcher.server.MinerWatcher;
import com.teamfractal.miner_watcher.server.config.MWServerConfig;
import net.minecraft.util.JSONUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * @author John-Paul-R
 */
public abstract class JsonText {

    private static final Gson GSON = new Gson();
    private static final Pattern TOKEN_PATTERN = Pattern.compile("%(\\d+\\$)?[\\d.]*[df]");
    public static final String DEFAULT_LANGUAGE = "en_us";

    private static final JsonText instance = create(MWServerConfig.LANGUAGE.get());

    private JsonText() {}

    private static JsonText create(String langId) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Objects.requireNonNull(builder);
        BiConsumer<String, String> biConsumer = builder::put;
        final String resourceFString = "/assets/miner_watcher/lang/%s.json";
        final String resourceLocation = String.format(resourceFString, langId);
        try {
            InputStream inputStream = JsonText.class.getResourceAsStream(resourceLocation);
            if (inputStream == null) {
                MinerWatcher.LOGGER.info(String.format("No Miner Watcher lang file for the language '%s' found. Make it to 'en_us' by default.", langId));
                inputStream = JsonText.class.getResourceAsStream(String.format(resourceFString, DEFAULT_LANGUAGE));
            }

            try {
                load(inputStream, biConsumer);
            } catch (Throwable var7) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (JsonParseException | IOException var8) {
            MinerWatcher.LOGGER.error("Couldn't read strings from {}", resourceLocation, var8);
        }

        final Map<String, String> map = builder.build();
        return new JsonText() {
            public String get(String key) {
                return map.getOrDefault(key, key);
            }
        };
    }

    public static void load(InputStream inputStream, BiConsumer<String, String> entryConsumer) {
        JsonObject jsonObject = GSON.fromJson(new InputStreamReader(inputStream, StandardCharsets.UTF_8), JsonObject.class);

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            Map.Entry<String, JsonElement> entry = (Map.Entry) stringJsonElementEntry;
            String string = TOKEN_PATTERN.matcher(JSONUtils.convertToString(entry.getValue(), entry.getKey())).replaceAll("%$1s");
            entryConsumer.accept(entry.getKey(), string);
        }

    }

    public static JsonText getInstance() {
        return instance;
    }

    public abstract String get(String key);

}
