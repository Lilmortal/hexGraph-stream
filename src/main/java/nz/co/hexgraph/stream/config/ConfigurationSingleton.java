package nz.co.hexgraph.stream.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationSingleton {
    public static final Logger LOG = LoggerFactory.getLogger(ConfigurationSingleton.class);

    private static final String CONFIG_NAME = "config.properties";

    private static final String TOPIC_HEX_CODE_CONFIG = "topic.hex.code";

    private static final String TOPIC_RESULT = "topic.result";

    private static final String APPLICATION_ID_CONFIG = "application.id.config";

    private static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers.config";

    private static final String DEFAULT_KEY_SERDE_CLASS_CONFIG = "default.key.serde.class.config";

    private static final String DEFAULT_VALUE_SERDE_CLASS_CONFIG = "default.value.serde.class.config";

    private String topicHexCode;

    private String topicResult;

    private String applicationIdConfig;

    private String bootstrapServersConfig;

    private String defaultKeySerdeClassConfig;

    private String defaultValueSerdeClassConfig;

    private ConfigurationSingleton() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        topicHexCode = properties.getProperty(TOPIC_HEX_CODE_CONFIG);

        topicResult = properties.getProperty(TOPIC_RESULT);

        applicationIdConfig = properties.getProperty(APPLICATION_ID_CONFIG);

        bootstrapServersConfig = properties.getProperty(BOOTSTRAP_SERVERS_CONFIG);

        defaultKeySerdeClassConfig = properties.getProperty(DEFAULT_KEY_SERDE_CLASS_CONFIG);

        defaultValueSerdeClassConfig = properties.getProperty(DEFAULT_VALUE_SERDE_CLASS_CONFIG);
    }

    private static class SingletonHelper {
        private static final ConfigurationSingleton INSTANCE = new ConfigurationSingleton();
    }

    public static ConfigurationSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public String getTopicHexCode() {
        return topicHexCode;
    }

    public String getTopicResult() {
        return topicResult;
    }

    public String getApplicationIdConfig() {
        return applicationIdConfig;
    }

    public String getBootstrapServersConfig() {
        return bootstrapServersConfig;
    }

    public String getDefaultKeySerdeClassConfig() {
        return defaultKeySerdeClassConfig;
    }

    public String getDefaultValueSerdeClassConfig() {
        return defaultValueSerdeClassConfig;
    }
}
