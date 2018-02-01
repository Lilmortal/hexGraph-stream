package nz.co.hexgraph.stream;

import nz.co.hexgraph.stream.config.Configuration;
import nz.co.hexgraph.stream.config.StreamConfigPropertiesBuilder;
import nz.co.hexgraph.stream.imagehex.ImageHex;
import nz.co.hexgraph.stream.imagehex.ImageHexSerde;
import nz.co.hexgraph.stream.imagehexaggregation.ImageAggregation;
import nz.co.hexgraph.stream.imagehexaggregation.ImageAggregationSerde;
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
        KStream<String, ImageHex> source = streamsBuilder.stream(topicHexValue,
                Consumed.with(new Serdes.StringSerde(), new ImageHexSerde()));

//        source.foreach((k,v) -> LOGGER.info(k + " ===== " + v));
        source.groupByKey().aggregate(ImageAggregation::new, (key, value, agg) -> {
            agg.imagePath = key;
            agg.counts.put(value.hexCode, agg.counts.getOrDefault(value.hexCode, 0) + 1);
            return agg;
        }, Materialized.with(new Serdes.StringSerde(), new ImageAggregationSerde())).toStream().to(configuration.getTopicResult(), Produced.with(Serdes.String(), new ImageAggregationSerde()));

        final Topology topology = streamsBuilder.build();

        final KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfigProperties);

        kafkaStreams.start();
    }
}
