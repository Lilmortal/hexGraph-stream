package nz.co.hexgraph.stream.imagehexaggregation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
            Optional<JsonNode> jsonNode = Optional.ofNullable(new ObjectMapper().readTree(data));
            imageAggregation = new ImageAggregation();

            if (jsonNode.isPresent()) {
                imageAggregation.imagePath = jsonNode.get().get("imagePath").asText();

//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//                LocalDateTime dateTime = LocalDateTime.parse(jsonNode.get().get("creationDate").asText(), formatter);
//                imageAggregation.creationDate = dateTime;

                if (jsonNode.get().get("counts") != null) {
                    JsonNode a = jsonNode.get().get("counts");
                    String b = a.toString();
                    TypeReference<HashMap<String, Integer>> typeRef = new TypeReference<HashMap<String, Integer>>() {};
                    Map<String, Integer> countsMap = new ObjectMapper().readValue(b, typeRef);
                    imageAggregation.counts = countsMap;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageAggregation;
    }

    @Override
    public void close() {

    }
}
