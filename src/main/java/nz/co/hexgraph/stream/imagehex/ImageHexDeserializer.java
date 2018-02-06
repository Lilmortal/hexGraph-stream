package nz.co.hexgraph.stream.imagehex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(data);
            imageHex = new ImageHex();

            imageHex.creationDate = jsonNode.get("creationDate").asText();
            // TODO: What happen if hexCode doesnt exist
            imageHex.hexCode = jsonNode.get("hexCode").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageHex;
    }

    @Override
    public void close() {

    }
}
