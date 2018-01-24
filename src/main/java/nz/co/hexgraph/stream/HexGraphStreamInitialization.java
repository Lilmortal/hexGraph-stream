package nz.co.hexgraph.stream;

import nz.co.hexgraph.stream.config.Configuration;
import nz.co.hexgraph.stream.config.StreamConfigPropertiesBuilder;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HexGraphStreamInitialization {
    public static final Logger LOGGER = LoggerFactory.getLogger(HexGraphStreamInitialization.class);

    private Configuration configuration;

    public HexGraphStreamInitialization(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        Properties streamConfigProperties = new StreamConfigPropertiesBuilder(configuration.getApplicationIdConfig(),
                configuration.getBootstrapServersConfig(), configuration.getDefaultKeySerdeClassConfig(), configuration.getDefaultValueSerdeClassConfig()).build();

        String topicHexValue = configuration.getTopicHexValue();
        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> source = streamsBuilder.stream(topicHexValue);

        source.groupByKey().count().toStream().foreach((s, s2) -> LOGGER.info(s + ": " + s2));

        final Topology topology = streamsBuilder.build();

        final KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfigProperties);

        kafkaStreams.start();
    }
}
