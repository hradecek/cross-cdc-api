package com.cross_ni.cross.cdc.topology;

import com.cross_ni.cross.cdc.model.source.Node;
import com.cross_ni.cross.cdc.serialization.avro.AvroSerdes;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;

// TODO: Use String as default key Serde
class NodeTopologyBuilder {

    private static final String KTABLE_NODES = "cross.cdc.ktable.node";
    private static final String TOPIC_NAME_NODE_TYPE = "crossdb.public.node";

    private final StreamsBuilder builder = new StreamsBuilder();

    public Topology build() {
        KTable<String, Node> nodes = builder.table(TOPIC_NAME_NODE_TYPE, Consumed.<String, Node>as(KTABLE_NODES).withValueSerde(AvroSerdes.Node()));
        nodes.toStream().foreach((k, v) -> System.out.println(k + " " + v));
        return builder.build();
    }
}
