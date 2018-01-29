package nz.co.hexgraph.stream.imageaggregation;

public class ImageAggregation {
    public String imagePath;

    public String hexCode;

    public long count;

    @Override
    public String toString() {
        return "ImageAggregation{" +
                "imagePath='" + imagePath + '\'' +
                ", hexCode='" + hexCode + '\'' +
                ", count=" + count +
                '}';
    }
}
