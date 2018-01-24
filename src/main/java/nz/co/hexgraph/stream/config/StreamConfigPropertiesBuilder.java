package nz.co.hexgraph.stream.config;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class StreamConfigPropertiesBuilder {
    private Properties properties = new Properties();

    public StreamConfigPropertiesBuilder(String applicationIdConfig, String bootstrapServersConfig, Class<? extends Serde> defaultKeySerdeClassConfig,
                                         Class<? extends Serde> defaultValueSerdeClassConfig) {
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationIdConfig);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, defaultKeySerdeClassConfig);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, defaultValueSerdeClassConfig);
    }

    public Properties build() {
        return properties;
    }
}
