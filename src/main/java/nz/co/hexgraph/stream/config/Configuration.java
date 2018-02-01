package nz.co.hexgraph.stream.config;

import org.apache.kafka.common.serialization.Serde;

public interface Configuration {
    String getTopicHexValue();

    String getTopicResult();

    String getApplicationIdConfig();

    String getBootstrapServersConfig();

    Class<? extends Serde> getDefaultKeySerdeClassConfig();

    Class<? extends Serde> getDefaultValueSerdeClassConfig();
}
