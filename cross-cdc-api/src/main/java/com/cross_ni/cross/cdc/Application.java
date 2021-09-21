package com.cross_ni.cross.cdc;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Application {

	public static void main(String[] args) {
		final Topology topology = NodeTopology.build();
		System.out.println(topology.describe());
		final Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");

		final KafkaStreams streams = new KafkaStreams(topology, props);
		streams.setUncaughtExceptionHandler(throwable -> {
			throwable.printStackTrace();
			return null;
		});
		Runtime.getRuntime()
				.addShutdownHook(
						new Thread(() -> {
							System.out.println("Exiting......");
							try {
								streams.close();
								System.out.println("Kafka Stream services stopped");
							} catch (Exception ex) {
								ex.printStackTrace();
								System.err.println(ex.getMessage());
							}
						})
				);
		streams.start();
	}
}
