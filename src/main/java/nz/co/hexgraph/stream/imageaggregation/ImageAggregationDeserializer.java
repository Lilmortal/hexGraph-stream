package nz.co.hexgraph.stream.imageaggregation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class ImageAggregationDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        ImageAggregation imageAggregation = null;
        if (data == null) {
            return null;
        }
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(data);
            imageAggregation = new ImageAggregation();
            imageAggregation.imagePath = jsonNode.get("imagePath").asText();
            imageAggregation.hexCode = jsonNode.get("hexCode").asText();
            imageAggregation.count = jsonNode.get("count").asLong();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageAggregation;
    }

    @Override
    public void close() {

    }
}
