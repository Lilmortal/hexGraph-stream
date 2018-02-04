package nz.co.hexgraph.stream.imagehexaggregation;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ImageAggregation {
    public String imagePath;

    public LocalDateTime creationDate;

    public Map<String, Integer> counts = new HashMap<>();

}
