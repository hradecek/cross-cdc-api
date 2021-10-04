package com.cross_ni.cross.cdc;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Class responsible for creation of {@link KafkaStreams application} with provided configuration and specified {@link Topology}.
 */
public class KafkaStreamsRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamsRunner.class);

    // TODO: Move to configuration file or something and inject from the Main instead
    private static final String APPLICATION_ID = "cross-cdc-v1";
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String STATE_DIR_CONFIG = "/tmp/" + APPLICATION_ID;

    /**
     * Start kafka streams application for specified {@code topology}.
     *
     * @param topology topology to be used
     */
    public void start(final Topology topology) {
        final KafkaStreams streams = new KafkaStreams(topology, properties());
        streams.setUncaughtExceptionHandler(KafkaStreamsRunner::uncaughtExceptionHandler);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownHook(streams)));

        streams.start();
    }

    private static Properties properties() {
        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        properties.put(StreamsConfig.STATE_DIR_CONFIG, STATE_DIR_CONFIG);
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        return properties;
    }

    private static StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse uncaughtExceptionHandler(final Throwable throwable) {
        throwable.printStackTrace();
        return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.SHUTDOWN_APPLICATION;
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
}
