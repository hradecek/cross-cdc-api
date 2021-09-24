package com.cross_ni.cross.cdc.serialization.avro;

import com.cross_ni.cross.cdc.model.source.Node;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;

import java.util.Collections;
import java.util.Map;

public class AvroSerdes {

    private static final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "http://localhost:8081");

    public static Serde<Node> Node() {
        final Serde<Node> nodeSerde = new SpecificAvroSerde<>();
        nodeSerde.configure(serdeConfig, false);
        return nodeSerde;
    }
}
