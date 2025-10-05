package config;

import org.aeonbits.owner.ConfigCache;

public class ConfigProvider {

    private ConfigProvider() {
    }

    public static WebConfig getWebConfig() {
        return ConfigCache.getOrCreate(WebConfig.class);
    }

    public static ApiConfig getApiConfig() {
        return ConfigCache.getOrCreate(ApiConfig.class);
    }
}