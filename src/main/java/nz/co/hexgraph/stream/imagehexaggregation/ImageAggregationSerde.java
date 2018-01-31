package nz.co.hexgraph.stream.imagehexaggregation;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ImageAggregationSerde implements Serde<ImageAggregation> {
    private ImageAggregationSerializer imageAggregationSerializer = new ImageAggregationSerializer();
    private ImageAggregationDeserializer imageAggregationDeserializer = new ImageAggregationDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        imageAggregationSerializer.configure(configs, isKey);
        imageAggregationDeserializer.configure(configs, isKey);
    }

    @Override
    public void close() {
        imageAggregationSerializer.close();
        imageAggregationDeserializer.close();
    }

    @Override
    public Serializer<ImageAggregation> serializer() {
        return imageAggregationSerializer;
    }

    @Override
    public Deserializer<ImageAggregation> deserializer() {
        return imageAggregationDeserializer;
    }
}