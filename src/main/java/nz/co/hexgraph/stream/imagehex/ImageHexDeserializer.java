package nz.co.hexgraph.stream.imagehex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.hexgraph.stream.imagehexaggregation.ImageAggregation;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class ImageHexDeserializer implements Deserializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        ImageHex imageHex = null;
        if (data == null) {
            return null;
        }
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(data);
            imageHex = new ImageHex();
            // TODO: What happen if hexValue doesnt exist
            imageHex.hexCode = jsonNode.get("hexValue").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageHex;
    }

    @Override
    public void close() {

    }
}
