package nz.co.hexgraph.stream;

import nz.co.hexgraph.stream.config.Configuration;
import nz.co.hexgraph.stream.config.StreamConfigPropertiesBuilder;
import nz.co.hexgraph.stream.imageaggregation.ImageAggregation;
import nz.co.hexgraph.stream.imageaggregation.ImageAggregationSerde;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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
        // TODO: See if can update generics automatically via config.properties
        KStream<String, String> source = streamsBuilder.stream(topicHexValue,
                Consumed.with(new Serdes.StringSerde(), new Serdes.StringSerde()));

//        source.foreach((k,v) -> LOGGER.info(k + " ===== " + v));
        source.groupByKey().aggregate(ImageAggregation::new, (key, value, agg) -> {
            agg.imagePath = key;
            agg.hexCode = value;
            agg.count = agg.count + 1;
            LOGGER.info(key + " -=-=-= " + value);
            LOGGER.info(agg.toString());
            return agg;
        }, Materialized.with(new Serdes.StringSerde(), new ImageAggregationSerde()));

        source.groupByKey().reduce((v1, v2) -> {
            LOGGER.info(v1 + " " + v2);
            return v2;
        });

        final Topology topology = streamsBuilder.build();

        final KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfigProperties);

        kafkaStreams.start();
    }
}
