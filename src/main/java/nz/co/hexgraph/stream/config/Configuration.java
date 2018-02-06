package nz.co.hexgraph.stream.config;

import org.apache.kafka.common.serialization.Serde;

public interface Configuration {
    String getTopicHexCode();

    String getTopicResult();

    String getApplicationIdConfig();

    String getBootstrapServersConfig();

    Class<? extends Serde> getDefaultKeySerdeClassConfig();

    Class<? extends Serde> getDefaultValueSerdeClassConfig();
}
