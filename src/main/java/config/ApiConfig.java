package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:api.properties"
})
public interface ApiConfig extends Config {

    @Key("api.base.url")
    @DefaultValue("https://petstore.swagger.io/v2")
    String baseUrl();

    @Key("api.timeout.connection")
    @DefaultValue("10000")
    int connectionTimeout();

    @Key("api.timeout.read")
    @DefaultValue("30000")
    int readTimeout();

    @Key("api.timeout.write")
    @DefaultValue("30000")
    int writeTimeout();

    @Key("api.log.requests")
    @DefaultValue("true")
    boolean logRequests();

    @Key("api.log.responses")
    @DefaultValue("true")
    boolean logResponses();
}