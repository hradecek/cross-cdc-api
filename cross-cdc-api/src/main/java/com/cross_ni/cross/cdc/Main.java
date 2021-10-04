package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.topology.CdcTopology;

/**
 * Application's entry point.
 * <p>
 * This class is responsible for setting up the application, reading configuration, starting kafka streams application etc.
 */
public class Main {

    /**
     * Main method, which servers as an entry point to application.
     * <p>
     * Set-ups and runs whole application.
     *
     * @param args arguments passed to application
     */
    public static void main(String[] args) {
        // Run IT!
        new KafkaStreamsRunner().start(new CdcTopology().create());
    }
}
