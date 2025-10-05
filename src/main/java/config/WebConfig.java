package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:web.properties"
})
public interface WebConfig extends Config {

    @Key("web.base.url")
    @DefaultValue("https://www.saucedemo.com")
    String baseUrl();

    @Key("browser.name")
    @DefaultValue("chrome")
    String browserName();

    @Key("browser.headless")
    @DefaultValue("false")
    boolean isHeadless();

    @Key("browser.window.width")
    @DefaultValue("1920")
    int browserWidth();

    @Key("browser.window.height")
    @DefaultValue("1080")
    int browserHeight();

    @Key("timeout.default")
    @DefaultValue("10000")
    long defaultTimeout();

    @Key("timeout.page.load")
    @DefaultValue("30000")
    long pageLoadTimeout();

    @Key("remote.url")
    String remoteUrl();

    @Key("browser.version")
    String browserVersion();
}