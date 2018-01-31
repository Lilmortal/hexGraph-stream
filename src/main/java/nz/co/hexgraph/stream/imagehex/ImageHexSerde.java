package nz.co.hexgraph.stream.imagehex;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ImageHexSerde implements Serde<ImageHex> {
    private ImageHexSerializer imageHexSerializer = new ImageHexSerializer();
    private ImageHexDeserializer imageHexDeserializer = new ImageHexDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {
        imageHexSerializer.close();
        imageHexDeserializer.close();
    }

    @Override
    public Serializer<ImageHex> serializer() {
        return imageHexSerializer;
    }

    @Override
    public Deserializer<ImageHex> deserializer() {
        return imageHexDeserializer;
    }
}
