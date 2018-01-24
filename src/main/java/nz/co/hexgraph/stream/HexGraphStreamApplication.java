package nz.co.hexgraph.stream;

import nz.co.hexgraph.stream.config.Configuration;
import nz.co.hexgraph.stream.config.ConfigurationImpl;

public class HexGraphStreamApplication {
    public static void main(String[] args) {
        Configuration configuration = new ConfigurationImpl();

        HexGraphStreamInitialization hexGraphStreamInitialization = new HexGraphStreamInitialization(configuration);
        hexGraphStreamInitialization.start();
    }
}
