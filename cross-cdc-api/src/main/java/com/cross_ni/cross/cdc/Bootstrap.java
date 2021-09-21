package com.cross_ni.cross.cdc;

import com.cross_ni.cross.cdc.topology.CdcTopology;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;

public class Bootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    // TODO: Move to configuration
    private static final String APPLICATION_ID = "cross-cdc-v1";
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String STATE_DIR_CONFIG = "/tmp/" + APPLICATION_ID;

    public static void main(String[] args) {
        // Run IT!
        streams(topology()).start();
    }

    private static KafkaStreams streams(final Topology topology) {
        final KafkaStreams streams = new KafkaStreams(topology, properties());
        streams.setUncaughtExceptionHandler(Bootstrap::uncaughtExceptionHandler);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownHook(streams)));

        return streams;
    }

    private static StreamThreadExceptionResponse uncaughtExceptionHandler(final Throwable throwable) {
        throwable.printStackTrace();
        return StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
    }

    private static void shutdownHook(final KafkaStreams streams) {
        try {
            streams.close();
            LOGGER.info("{} stopped", APPLICATION_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error(ex.getMessage());
        }
    }

    private static Topology topology() {
        final Topology topology = CdcTopology.build();
        LOGGER.info(topology.describe().toString());

        return topology;
    }

    private static Properties properties() {
        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.STATE_DIR_CONFIG, STATE_DIR_CONFIG);

        return properties;
    }
}
