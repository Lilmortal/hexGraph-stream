package nz.co.hexgraph.stream.config;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Configuration {
    public static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private static final String APPLICATION_ID_CONFIG = "application.id.config";

    private static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers.config";

    private static final String DEFAULT_KEY_SERDE_CLASS_CONFIG = "default.key.serde.class.config";

    private static final String DEFAULT_VALUE_SERDE_CLASS_CONFIG = "default.value.serde.class.config";

    private String applicationIdConfig;

    private String bootstrapServersConfig;

    private Class<? extends Serde> defaultKeySerdeClassConfig;

    private Class<? extends Serde> defaultValueSerdeClassConfig;

    private Configuration() {
        Properties properties = new Properties();

        applicationIdConfig = properties.getProperty(APPLICATION_ID_CONFIG);

        bootstrapServersConfig = properties.getProperty(BOOTSTRAP_SERVERS_CONFIG);

        defaultKeySerdeClassConfig = getSerdeClass(properties.getProperty(DEFAULT_KEY_SERDE_CLASS_CONFIG));

        defaultValueSerdeClassConfig = getSerdeClass(properties.getProperty(DEFAULT_VALUE_SERDE_CLASS_CONFIG));
    }

    private static class SingletonHelper {
        private static final Configuration INSTANCE = new Configuration();
    }

    public static Configuration getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public String getApplicationIdConfig() {
        return applicationIdConfig;
    }

    public String getBootstrapServersConfig() {
        return bootstrapServersConfig;
    }

    public Class<? extends Serde> getDefaultKeySerdeClassConfig() {
        return defaultKeySerdeClassConfig;
    }

    public Class<? extends Serde> getDefaultValueSerdeClassConfig() {
        return defaultValueSerdeClassConfig;
    }

    private Class<? extends Serde> getSerdeClass(String serdes) {
        Class<? extends Serde> serdesName;

        switch (serdes.toUpperCase()) {
            case "STRING": {
                serdesName = Serdes.String().getClass();
            }
            default: {
                serdesName = null;
            }
        }
        return serdesName;
    }
}
