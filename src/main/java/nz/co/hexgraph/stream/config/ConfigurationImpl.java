package nz.co.hexgraph.stream.config;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class ConfigurationImpl implements Configuration {
    ConfigurationSingleton configurationSingleton = ConfigurationSingleton.getInstance();

    @Override
    public String getTopicHexCode() {
        return configurationSingleton.getTopicHexCode();
    }

    @Override
    public String getTopicResult() {
        return configurationSingleton.getTopicResult();
    }

    @Override
    public String getApplicationIdConfig() {
        return configurationSingleton.getApplicationIdConfig();
    }

    @Override
    public String getBootstrapServersConfig() {
        return configurationSingleton.getBootstrapServersConfig();
    }

    @Override
    public Class<? extends Serde> getDefaultKeySerdeClassConfig() {
        try {
            return getSerdeClass(configurationSingleton.getDefaultKeySerdeClassConfig());
        } catch (NullPointerException e) {
            throw new NullPointerException("Default key serde class config is not available in config file.");
        }
    }

    @Override
    public Class<? extends Serde> getDefaultValueSerdeClassConfig() {
        try {
            return getSerdeClass(configurationSingleton.getDefaultValueSerdeClassConfig());
        } catch (NullPointerException e) {
            throw new NullPointerException("Default value serde class config is not available in config file.");
        }
    }

    private Class<? extends Serde> getSerdeClass(String serdes) {
        Class<? extends Serde> serdesName;

        switch (serdes.toUpperCase()) {
            case "STRING":
                serdesName = Serdes.String().getClass();
                break;
            case "LONG":
                serdesName = Serdes.Long().getClass();
                break;
            default:
                throw new RuntimeException("It can't read " + serdes + " as a serdes type.");
        }
        return serdesName;
    }
}
